package io.ehdev.conrad.version.bumper.api;

import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;

public interface VersionBumperService {
    VersionBumper findVersionBumper(String className);

    CommitVersion findNextVersion(ResourceDetails repoModel, String commitId, String message, ApiCommitModel lastCommit);
}
