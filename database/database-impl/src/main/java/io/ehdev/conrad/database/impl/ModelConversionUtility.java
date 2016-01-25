package io.ehdev.conrad.database.impl;

import io.ehdev.conrad.database.impl.bumper.VersionBumperModel;
import io.ehdev.conrad.database.impl.commit.CommitModel;
import io.ehdev.conrad.database.impl.project.ProjectModel;
import io.ehdev.conrad.database.impl.repo.RepoModel;
import io.ehdev.conrad.database.impl.token.TokenType;
import io.ehdev.conrad.database.impl.token.UserTokenModel;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.database.model.user.ApiGeneratedUserToken;
import io.ehdev.conrad.database.model.user.ApiTokenType;
import io.ehdev.conrad.database.model.user.ApiUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelConversionUtility {

    public static ApiUser toApiModel(BaseUserModel user) {
        return new ApiUser(user.getId(), user.getName(), user.getEmailAddress());
    }

    public static TokenType toDatabaseModel(ApiTokenType type) {
        switch (type) {
            case USER:
                return TokenType.USER;
            case API:
                return TokenType.API;
            default:
                throw new IllegalArgumentException("Unknown type " + type.getType());
        }
    }

    public static ApiTokenType toDatabaseModel(TokenType type) {
        switch (type) {
            case USER:
                return ApiTokenType.USER;
            case API:
                return ApiTokenType.API;
            default:
                throw new IllegalArgumentException("Unknown type " + type.getName());
        }
    }

    public static ApiGeneratedUserToken toApiModel(UserTokenModel token) {
        return new ApiGeneratedUserToken(
            token.getId(),
            toDatabaseModel(token.getTokenType()), token.getCreatedAt(), token.getExpiresAt());
    }

    public static ApiVersionBumperModel toApiModel(VersionBumperModel bumper) {
        return new ApiVersionBumperModel(
            bumper.getId(),
            bumper.getClassName(),
            bumper.getDescription(),
            bumper.getBumperName());
    }

    public static ApiRepoModel toApiModel(RepoModel repo) {
        return new ApiRepoModel(
            repo.getId(),
            repo.getRepoName(),
            repo.getUrl(),
            repo.getVersionBumperModel().getBumperName(),
            repo.getProjectModel().getProjectName());
    }

    public static ApiProjectModel toApiModel(ProjectModel model) {
        List<RepoModel> repoModels = model.getRepoModels();
        ArrayList<ApiRepoModel> repos = new ArrayList<>();
        if (repoModels != null) {
            repos.addAll(repoModels.stream().map(ModelConversionUtility::toApiModel).collect(Collectors.toList()));
        }
        return new ApiProjectModel(model.getProjectName(), repos);
    }

    public static ApiFullCommitModel toApiModel(CommitModel commitModel) {
        return new ApiFullCommitModel(commitModel.getCommitId(), commitModel.getVersion());
    }
}
