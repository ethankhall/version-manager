package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.api.exception.TokenNotFoundException;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.permission.ProjectApiAuthentication;
import io.ehdev.conrad.database.model.permission.RepositoryApiAuthentication;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.enums.TokenType;
import io.ehdev.conrad.db.tables.TokenAuthenticationsTable;
import io.ehdev.conrad.db.tables.daos.*;
import io.ehdev.conrad.db.tables.pojos.*;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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

        UUID userId = null;
        UUID projectId = null;
        UUID repoId = null;
        TokenType type;

        if(authentication instanceof UserApiAuthentication) {
            userId = authentication.getUuid();
            type = TokenType.USER;
        } else if(authentication instanceof ProjectApiAuthentication) {
            projectId = authentication.getUuid();
            type = TokenType.PROJECT;
        } else {
            repoId = authentication.getUuid();
            type = TokenType.REPOSITORY;
        }

        TokenAuthentications tokens = createNewToken(now, type);
        tokenMapDao.insert(new TokenJoin(null, tokens.getUuid(), projectId, repoId, userId));

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
            ApiTokenType.parse(type.getName()),
            token.getCreatedAt().atZone(ZoneOffset.UTC),
            token.getExpiresAt().atZone(ZoneOffset.UTC));
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
}
