package io.ehdev.conrad.backend.version.commit.internal;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.CommitVersionBumper;

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
