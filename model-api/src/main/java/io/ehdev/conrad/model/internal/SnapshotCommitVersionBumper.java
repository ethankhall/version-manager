package io.ehdev.conrad.model.internal;

import io.ehdev.conrad.model.CommitVersion;
import io.ehdev.conrad.model.CommitVersionBumper;

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
