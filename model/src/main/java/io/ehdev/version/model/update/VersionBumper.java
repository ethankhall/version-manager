package io.ehdev.version.model.update;

import io.ehdev.version.model.commit.CommitDetails;
import io.ehdev.version.model.commit.CommitVersion;
import io.ehdev.version.model.commit.RepositoryCommit;

public interface VersionBumper {

    NextVersion createNextVersion(RepositoryCommit parent, CommitDetails commitDetails);

    CommitVersion createBuildVersion(RepositoryCommit repositoryCommit);
}
