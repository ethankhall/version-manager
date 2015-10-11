package io.ehdev.version.model.internal;

import io.ehdev.version.model.CommitVersion;
import io.ehdev.version.model.CommitVersionBumper;
import io.ehdev.version.model.VersionGroup;

import java.util.Arrays;

public class VersionCommitVersionBumper implements CommitVersionBumper, VersionGroup {

    final int groupNumber;

    public VersionCommitVersionBumper(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public CommitVersion bump(int[] groups, String postfix) {
        int[] nextVersionList = Arrays.copyOf(groups, Math.max(3, groupNumber + 1));
        //Set the lower digits to 0
        for (int i = groupNumber + 1; i < groups.length; i++) {
            nextVersionList[i] = 0;
        }

        //Bump the version
        nextVersionList[groupNumber]++;

        return new DefaultCommitVersion(nextVersionList, null);
    }

    @Override
    public int getGroup() {
        return groupNumber;
    }
}
