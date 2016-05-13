package io.ehdev.conrad.database.api.internal.v2;

import io.ehdev.conrad.database.api.exception.TokenNotFoundException;
import io.ehdev.conrad.database.api.v2.TokenManagementApiV2;
import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
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
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultTokenManagementApiV2 implements TokenManagementApiV2 {

    private final DSLContext dslContext;
    private final TokenAuthenticationsDao tokensDao;
    private final ProjectDetailsDao projectDetailsDao;
    private final TokenJoinDao tokenJoinDao;
    private final RepoDetailsDao repoDetailsDao;
    private final UserDetailsDao userDetailsDao;

    @Autowired
    public DefaultTokenManagementApiV2(DSLContext dslContext,
                                     TokenAuthenticationsDao tokensDao,
                                     ProjectDetailsDao projectDetailsDao,
                                     TokenJoinDao tokenJoinDao,
                                     RepoDetailsDao repoDetailsDao,
                                     UserDetailsDao userDetailsDao) {
        this.dslContext = dslContext;
        this.tokensDao = tokensDao;
        this.projectDetailsDao = projectDetailsDao;
        this.tokenJoinDao = tokenJoinDao;
        this.repoDetailsDao = repoDetailsDao;
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public ApiGeneratedUserToken createToken(ApiTokenAuthentication authentication) {
        Instant now = Instant.now();

        DefaultTokenManagementApiV2.ParsedToken parsedToken = DefaultTokenManagementApiV2.ParsedToken.from(authentication);

        TokenAuthentications tokens = createNewToken(now, parsedToken.type);
        tokenJoinDao.insert(new TokenJoin(null, tokens.getUuid(), parsedToken.projectId, parsedToken.repoId, parsedToken.userId));

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
    public ApiGeneratedUserToken createToken(ResourceDetails resourceDetails) {
        UUID projectId = null;
        UUID repoId = null;
        TokenType type;

        if (resourceDetails.getRepoId() != null) {
            repoId = resourceDetails.getRepoId().getId();
            type = TokenType.REPOSITORY;
        } else {
            projectId = resourceDetails.getProjectId().getId();
            type = TokenType.PROJECT;
        }

        TokenAuthentications token = createNewToken(Instant.now(), type);
        tokenJoinDao.insert(new TokenJoin(null, token.getUuid(), projectId, repoId, null));

        return new ApiGeneratedUserToken(
            token.getUuid(),
            ApiTokenType.parse(type.getLiteral()),
            token.getCreatedAt().atZone(ZoneOffset.UTC),
            token.getExpiresAt().atZone(ZoneOffset.UTC));
    }

    @Override
    public List<RetrievedToken> getTokens(ResourceDetails resourceDetails) {
        if (resourceDetails.getRepoId() != null) {
            return getTokens(DefaultTokenManagementApiV2.ParsedToken.withRepository(resourceDetails.getRepoId().getId()));
        } else {
            return getTokens(DefaultTokenManagementApiV2.ParsedToken.withProject(resourceDetails.getProjectId().getId()));
        }
    }

    @Override
    public List<RetrievedToken> getTokens(ApiTokenAuthentication authentication) {
        return getTokens(DefaultTokenManagementApiV2.ParsedToken.from(authentication));
    }

    public List<RetrievedToken> getTokens(DefaultTokenManagementApiV2.ParsedToken parsedToken) {
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
        TokenJoin tokenJoin = tokenJoinDao.fetchOneByUuid(tokenId);
        invalidateTokenValid(tokenJoin.getToken());
    }

    private void invalidateTokenValid(UUID apiToken) {
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
        TokenJoin tokenMap = tokenJoinDao.fetchOneByToken(apiToken.getUuid());
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

        static DefaultTokenManagementApiV2.ParsedToken withUser(UUID userId) {
            return new DefaultTokenManagementApiV2.ParsedToken(userId, null, null, TokenType.USER);
        }
        static DefaultTokenManagementApiV2.ParsedToken withProject(UUID projectId) {
            return new DefaultTokenManagementApiV2.ParsedToken(null, projectId, null, TokenType.PROJECT);
        }

        static DefaultTokenManagementApiV2.ParsedToken withRepository(UUID repoId) {
            return new DefaultTokenManagementApiV2.ParsedToken(null, null, repoId, TokenType.REPOSITORY);
        }

        static DefaultTokenManagementApiV2.ParsedToken from(ApiTokenAuthentication authentication) {
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
