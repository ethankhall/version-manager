package io.ehdev.version.update;

import io.ehdev.version.commit.CommitDetails;
import io.ehdev.version.commit.CommitVersion;
import io.ehdev.version.commit.RepositoryCommit;

public interface VersionBumper {

    NextVersion createNextVersion(RepositoryCommit parent, CommitDetails commitDetails);

    CommitVersion createBuildVersion(RepositoryCommit repositoryCommit);

    String name();
}
