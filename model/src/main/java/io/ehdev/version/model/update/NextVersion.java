package io.ehdev.version.model.update;

import io.ehdev.version.model.commit.CommitVersion;

public interface NextVersion {
    CommitVersion getCommitVersion();
    Type getType();

    enum Type {
        NEXT,
        BUGFIX
    }
}
