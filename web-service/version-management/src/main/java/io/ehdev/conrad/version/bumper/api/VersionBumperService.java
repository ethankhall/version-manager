package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.database.model.project.ApiQualifiedRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;

import java.util.List;

public interface VersionBumperService {
    VersionBumper findVersionBumper(String className);

    CommitVersion findNextVersion(ApiQualifiedRepoModel repoModel, String commitId, String message, CommitVersion lastCommit);

    ApiFullCommitModel findLatestCommitVersion(ApiQualifiedRepoModel repoModel, List<ApiCommitModel> history);
}
