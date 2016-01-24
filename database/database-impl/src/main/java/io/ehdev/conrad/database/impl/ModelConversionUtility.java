package io.ehdev.conrad.database.impl;

import io.ehdev.conrad.database.impl.bumper.VersionBumperModel;
import io.ehdev.conrad.database.impl.commit.CommitModel;
import io.ehdev.conrad.database.impl.project.ProjectModel;
import io.ehdev.conrad.database.impl.repo.RepoModel;
import io.ehdev.conrad.database.impl.token.TokenType;
import io.ehdev.conrad.database.impl.token.UserTokenModel;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import io.ehdev.conrad.model.internal.ApiCommit;
import io.ehdev.conrad.model.project.ApiProject;
import io.ehdev.conrad.model.internal.ApiRepo;
import io.ehdev.conrad.model.internal.ApiVersionBumper;
import io.ehdev.conrad.model.user.ConradGeneratedToken;
import io.ehdev.conrad.model.user.ConradTokenType;
import io.ehdev.conrad.model.user.ConradUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelConversionUtility {

    public static ConradUser toApiModel(BaseUserModel user) {
        return new ConradUser(user.getId(), user.getName(), user.getEmailAddress());
    }

    public static TokenType toDatabaseModel(ConradTokenType type) {
        switch (type) {
            case USER:
                return TokenType.USER;
            case API:
                return TokenType.API;
            default:
                throw new IllegalArgumentException("Unknown type " + type.getType());
        }
    }

    public static ConradTokenType toDatabaseModel(TokenType type) {
        switch (type) {
            case USER:
                return ConradTokenType.USER;
            case API:
                return ConradTokenType.API;
            default:
                throw new IllegalArgumentException("Unknown type " + type.getName());
        }
    }

    public static ConradGeneratedToken toApiModel(UserTokenModel token) {
        return new ConradGeneratedToken(token.getId(), toDatabaseModel(token.getTokenType()), token.getCreatedAt(), token.getExpiresAt());
    }

    public static ApiVersionBumper toApiModel(VersionBumperModel bumper) {
        return new ApiVersionBumper(bumper.getId(), bumper.getClassName(), bumper.getDescription(), bumper.getBumperName());
    }

    public static ApiRepo toApiModel(RepoModel repo) {
        return new ApiRepo(
            repo.getId(),
            repo.getRepoName(),
            repo.getUrl(),
            repo.getVersionBumperModel().getBumperName(),
            repo.getProjectModel().getProjectName());
    }

    public static ApiProject toApiModel(ProjectModel model) {
        List<RepoModel> repoModels = model.getRepoModels();
        ArrayList<ApiRepo> repos = new ArrayList<>();
        if(repoModels != null) {
            repos.addAll(repoModels.stream().map(ModelConversionUtility::toApiModel).collect(Collectors.toList()));
        }
        return new ApiProject(model.getProjectName(), repos);
    }

    public static ApiCommit toApiModel(CommitModel commitModel) {
        CommitModel parentCommit = commitModel.getParentCommit();
        return new ApiCommit(commitModel.getCommitId(), commitModel.getVersion(), parentCommit == null ? null : parentCommit.getCommitId());
    }
}
