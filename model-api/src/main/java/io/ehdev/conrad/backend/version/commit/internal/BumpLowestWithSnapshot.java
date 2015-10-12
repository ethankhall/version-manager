package io.ehdev.conrad.backend.version.commit.internal;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.CommitVersionBumper;

import java.util.Arrays;

public class BumpLowestWithSnapshot implements CommitVersionBumper {
    @Override
    public CommitVersion bump(int[] groups, String postfix) {
        int[] ints = Arrays.copyOf(groups, groups.length);
        ints[groups.length]++;

        return new DefaultCommitVersion(ints, "SNAPSHOT");
    }
}
