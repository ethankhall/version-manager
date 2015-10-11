package io.ehdev.conrad.version.commit.internal;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;

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
