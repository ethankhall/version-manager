package io.ehdev.conrad.model.update;

import io.ehdev.version.model.CommitVersion;

public interface NextVersion {
    CommitVersion getCommitVersion();
    Type getType();

    enum Type {
        NEXT,
        BUGFIX
    }
}
