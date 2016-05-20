package io.ehdev.conrad.database.impl;

import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.db.tables.pojos.*;

import java.time.ZoneOffset;
import java.util.List;

public class ModelConversionUtility {

    public static DefaultApiRepoModel toApiModel(RepoDetails repo) {
        return new DefaultApiRepoModel(
            "",
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
        return new ApiCommitModel(commitDetails.getCommitId(),
            commitDetails.getVersion(),
            commitDetails.getCreatedAt().atZone(ZoneOffset.UTC));
    }

    public static UserApiAuthentication toApiModel(UserDetails userDetails) {
        return new UserApiAuthentication(userDetails.getUuid(), userDetails.getUserName(), userDetails.getName(), userDetails.getEmailAddress());
    }

}
