package io.ehdev.conrad.version.commit.internal;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;

public class ForcedVersionBumper implements CommitVersionBumper {

    private final CommitVersion version;

    public ForcedVersionBumper(CommitVersion version){
        this.version = version;
    }

    @Override
    public CommitVersion bump(int[] groups, String postfix) {
        return version;
    }
}
