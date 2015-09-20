package io.ehdev.version.update;

import io.ehdev.version.commit.CommitDetails;
import io.ehdev.version.commit.RepositoryCommit;

public interface VersionBumper {
    NextVersion createNextVersion(RepositoryCommit parent, CommitDetails commitDetails);
    String name();
}
