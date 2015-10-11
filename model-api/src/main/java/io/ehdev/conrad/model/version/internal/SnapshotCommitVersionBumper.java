package io.ehdev.conrad.model.version.internal;

import io.ehdev.conrad.model.version.CommitVersion;
import io.ehdev.conrad.model.version.CommitVersionBumper;

public class SnapshotCommitVersionBumper implements CommitVersionBumper {

    private final String postfix;

    public SnapshotCommitVersionBumper(String postfix) {
        this.postfix = postfix;
    }

    @Override
    public CommitVersion bump(int[] groups, String postfix) {
        return new DefaultCommitVersion(groups, this.postfix);
    }
}
