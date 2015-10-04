package io.ehdev.version.update;

import io.ehdev.version.commit.CommitVersion;

public interface NextVersion {
    CommitVersion getCommitVersion();
    Type getType();

    enum Type {
        NEXT,
        BUGFIX
    }
}
