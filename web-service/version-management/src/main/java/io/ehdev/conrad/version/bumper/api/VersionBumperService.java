package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;

import java.util.List;

public interface VersionBumperService {
    VersionBumper findVersionBumper(String className);

    CommitVersion findNextVersion(ApiRepoModel repoModel, String commitId, String message, CommitVersion lastCommit);

    ApiCommitModel findLatestCommitVersion(ApiRepoModel repoModel, List<ApiCommitModel> history);
}
