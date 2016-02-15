package io.ehdev.conrad.database.impl;

import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.database.model.user.ApiUser;
import io.ehdev.conrad.db.enums.TokenType;
import io.ehdev.conrad.db.tables.pojos.*;

import java.time.ZoneOffset;
import java.util.List;

public class ModelConversionUtility {

    public static DefaultApiRepoModel toApiModel(RepoDetails repo) {
        return new DefaultApiRepoModel(
            repo.getProjectName(),
            repo.getRepoName(),
            repo.getUrl());
    }

    public static ApiProjectModel toApiModel(ProjectDetails model, List<DefaultApiRepoModel> repoDetails) {
        return new ApiProjectModel(model.getProjectName(), repoDetails);
    }

    public static ApiVersionBumperModel toApiModel(VersionBumpers versionBumpers) {
        return new ApiVersionBumperModel(versionBumpers.getClassName(),
            versionBumpers.getDescription(),
            versionBumpers.getBumperName());
    }

    public static ApiCommitModel toApiModel(CommitDetails commitDetails) {
        return new ApiCommitModel(commitDetails.getCommitId(), commitDetails.getVersion());
    }

    public static ApiGeneratedUserToken toApiModel(UserTokens userToken) {
        return new ApiGeneratedUserToken(
            userToken.getUuid(),
            toApiModel(userToken.getTokenType()),
            userToken.getCreatedAt().atZone(ZoneOffset.UTC),
            userToken.getExpiresAt().atZone(ZoneOffset.UTC));
    }

    private static ApiTokenType toApiModel(TokenType tokenType) {
        switch (tokenType) {
            case USER:
                return ApiTokenType.USER;
            case API:
                return ApiTokenType.API;
            default:
                throw new RuntimeException("Unknown type " + tokenType.getName());
        }
    }

    public static ApiUser toApiModel(UserDetails userDetails) {
        return new ApiUser(userDetails.getUuid(), userDetails.getUserName(), userDetails.getName(), userDetails.getEmailAddress());
    }
}
