package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.api.exception.TokenNotFoundException;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.permission.ProjectApiAuthentication;
import io.ehdev.conrad.database.model.permission.RepositoryApiAuthentication;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.token.RetrievedToken;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.enums.TokenType;
import io.ehdev.conrad.db.tables.TokenAuthenticationsTable;
import io.ehdev.conrad.db.tables.TokenJoinTable;
import io.ehdev.conrad.db.tables.daos.*;
import io.ehdev.conrad.db.tables.pojos.*;
import io.ehdev.conrad.db.tables.records.TokenJoinRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultTokenManagementApi implements TokenManagementApi {

    private final DSLContext dslContext;
    private final TokenAuthenticationsDao tokensDao;
    private final ProjectDetailsDao projectDetailsDao;
    private final RepoManagementApiInternal repoManagementApiInternal;
    private final TokenJoinDao tokenMapDao;
    private final RepoDetailsDao repoDetailsDao;
    private final UserDetailsDao userDetailsDao;

    @Autowired
    public DefaultTokenManagementApi(DSLContext dslContext,
                                     TokenAuthenticationsDao tokensDao,
                                     ProjectDetailsDao projectDetailsDao,
                                     RepoManagementApiInternal repoManagementApiInternal,
                                     TokenJoinDao tokenMapDao,
                                     RepoDetailsDao repoDetailsDao,
                                     UserDetailsDao userDetailsDao) {
        this.dslContext = dslContext;
        this.tokensDao = tokensDao;
        this.projectDetailsDao = projectDetailsDao;
        this.repoManagementApiInternal = repoManagementApiInternal;
        this.tokenMapDao = tokenMapDao;
        this.repoDetailsDao = repoDetailsDao;
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public ApiGeneratedUserToken createToken(ApiTokenAuthentication authentication) {
        Instant now = Instant.now();

        ParsedToken parsedToken = ParsedToken.from(authentication);

        TokenAuthentications tokens = createNewToken(now, parsedToken.type);
        tokenMapDao.insert(new TokenJoin(null, tokens.getUuid(), parsedToken.projectId, parsedToken.repoId, parsedToken.userId));

        return new ApiGeneratedUserToken(
            tokens.getUuid(),
            ApiTokenType.USER,
            tokens.getCreatedAt().atZone(ZoneOffset.UTC),
            tokens.getExpiresAt().atZone(ZoneOffset.UTC));
    }

    private TokenAuthentications createNewToken(Instant now, TokenType type) {
        TokenAuthenticationsTable tokensTable = Tables.TOKEN_AUTHENTICATIONS;
        return dslContext
            .insertInto(tokensTable, tokensTable.CREATED_AT, tokensTable.EXPIRES_AT, tokensTable.VALID, tokensTable.TOKEN_TYPE)
            .values(now, now.plus(30, ChronoUnit.DAYS), true, type)
            .returning(tokensTable.fields())
            .fetchOne()
            .into(TokenAuthentications.class);
    }

    @Override
    public ApiGeneratedUserToken createToken(String projectName, String repoName) {
        UUID projectId = null;
        UUID repoId = null;
        TokenType type;

        if (repoName != null) {
            repoId = repoManagementApiInternal.findRepository(projectName, repoName).get().getUuid();
            type = TokenType.REPOSITORY;
        } else {
            projectId = projectDetailsDao.fetchOneByProjectName(projectName).getUuid();
            type = TokenType.PROJECT;
        }

        TokenAuthentications token = createNewToken(Instant.now(), type);
        tokenMapDao.insert(new TokenJoin(null, token.getUuid(), projectId, repoId, null));

        return new ApiGeneratedUserToken(
            token.getUuid(),
            ApiTokenType.parse(type.getLiteral()),
            token.getCreatedAt().atZone(ZoneOffset.UTC),
            token.getExpiresAt().atZone(ZoneOffset.UTC));
    }

    @Override
    public List<RetrievedToken> getTokens(String project, String repo) {
        ParsedToken parsedToken;
        if(repo != null) {
            parsedToken = ParsedToken.withRepository(repoManagementApiInternal.findRepository(project, repo).get().getUuid());
        } else {
            parsedToken = ParsedToken.withProject(projectDetailsDao.fetchOneByProjectName(project).getUuid());
        }
        return getTokens(parsedToken);
    }

    @Override
    public List<RetrievedToken> getTokens(ApiTokenAuthentication authentication) {
        return getTokens(ParsedToken.from(authentication));
    }

    public List<RetrievedToken> getTokens(ParsedToken parsedToken) {
        TokenAuthenticationsTable ta = Tables.TOKEN_AUTHENTICATIONS.as("ta");
        TokenJoinTable tj = Tables.TOKEN_JOIN.as("tj");
        return dslContext
            .select(tj.UUID, ta.CREATED_AT, ta.CREATED_AT)
            .from(ta)
            .join(tj).on(tj.TOKEN.eq(ta.UUID))
            .where(valueOrNull(parsedToken.userId, tj.USER_UUID))
                .and(valueOrNull(parsedToken.projectId, tj.PROJECT_UUID))
                .and(valueOrNull(parsedToken.repoId, tj.REPO_UUID))
                .and(ta.VALID.eq(true))
                .and(ta.EXPIRES_AT.greaterOrEqual(Clock.systemUTC().instant()))
            .fetch()
            .stream()
            .map(it -> new RetrievedToken(it.value1(), it.value2().atZone(ZoneOffset.UTC), it.value3().atZone(ZoneOffset.UTC)))
            .collect(Collectors.toList());
    }

    private <T> Condition valueOrNull(T value, TableField<TokenJoinRecord, T> field) {
        return value != null ? field.eq(value) : field.isNull();
    }

    @Override
    public boolean isTokenValid(ApiToken apiToken) {
        TokenAuthentications token = tokensDao.fetchOneByUuid(apiToken.getUuid());
        return isValid(token);
    }

    private boolean isValid(TokenAuthentications token) {
        return token != null
            && token.getValid()
            && Instant.now().isBefore(token.getExpiresAt());
    }

    @Override
    public void invalidateTokenValidByJoinId(UUID tokenId) {
        TokenJoin tokenJoin = tokenMapDao.fetchOneByUuid(tokenId);
        invalidateTokenValid(tokenJoin.getToken());
    }

    public void invalidateTokenValid(UUID apiToken) {
        TokenAuthentications token = tokensDao.fetchOneByUuid(apiToken);
        if (token == null) {
            throw new TokenNotFoundException(apiToken);
        }
        token.setValid(false);
        tokensDao.update(token);
    }

    @Override
    public void invalidateTokenValid(ApiToken apiToken) {
        TokenAuthentications token = tokensDao.fetchOneByUuid(apiToken.getUuid());
        if (token == null) {
            throw new TokenNotFoundException(apiToken);
        }
        token.setValid(false);
        tokensDao.update(token);
    }

    @Override
    public ApiTokenAuthentication findUser(ApiToken apiToken) {
        TokenJoin tokenMap = tokenMapDao.fetchOneByToken(apiToken.getUuid());
        if(tokenMap == null) {
            return null;
        }

        if(tokenMap.getProjectUuid() != null) {
            ProjectDetails projectDetails = projectDetailsDao.fetchOneByUuid(tokenMap.getProjectUuid());
            return new ProjectApiAuthentication(projectDetails.getUuid(), projectDetails.getProjectName());
        } else if(tokenMap.getRepoUuid() != null) {
            RepoDetails repoDetails = repoDetailsDao.fetchOneByUuid(tokenMap.getRepoUuid());
            return new RepositoryApiAuthentication(repoDetails.getUuid(), repoDetails.getProjectName(), repoDetails.getRepoName());
        } else {
            UserDetails userDetails = userDetailsDao.fetchOneByUuid(tokenMap.getUserUuid());
            return new UserApiAuthentication(userDetails.getUuid(), userDetails.getUserName(), userDetails.getName(), userDetails.getEmailAddress());
        }
    }

    private static class ParsedToken {
        private final UUID userId;
        private final UUID projectId ;
        private final UUID repoId;
        private final TokenType type;

        private ParsedToken(UUID userId, UUID projectId, UUID repoId, TokenType type) {
            this.userId = userId;
            this.projectId = projectId;
            this.repoId = repoId;
            this.type = type;
        }

        static ParsedToken withUser(UUID userId) {
            return new ParsedToken(userId, null, null, TokenType.USER);
        }
        static ParsedToken withProject(UUID projectId) {
            return new ParsedToken(null, projectId, null, TokenType.PROJECT);
        }

        static ParsedToken withRepository(UUID repoId) {
            return new ParsedToken(null, null, repoId, TokenType.REPOSITORY);
        }

        static ParsedToken from(ApiTokenAuthentication authentication) {
            if(authentication instanceof UserApiAuthentication) {
                return withUser(authentication.getUuid());
            } else if(authentication instanceof ProjectApiAuthentication) {
                return withProject(authentication.getUuid());
            } else {
                return withRepository(authentication.getUuid());
            }
        }

    }
}
