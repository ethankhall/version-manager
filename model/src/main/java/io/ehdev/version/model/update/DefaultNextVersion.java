package io.ehdev.version.model.update;

import io.ehdev.version.model.commit.CommitVersion;

public class DefaultNextVersion implements NextVersion {

    private final CommitVersion commitVersion;
    private final NextVersion.Type type;

    public DefaultNextVersion(CommitVersion commitVersion, Type type) {
        this.commitVersion = commitVersion;
        this.type = type;
    }

    @Override
    public CommitVersion getCommitVersion() {
        return commitVersion;
    }

    @Override
    public Type getType() {
        return type;
    }
}
