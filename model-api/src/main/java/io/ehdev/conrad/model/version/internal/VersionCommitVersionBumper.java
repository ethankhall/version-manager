package io.ehdev.conrad.model.version.internal;

import io.ehdev.conrad.model.version.CommitVersion;
import io.ehdev.conrad.model.version.CommitVersionBumper;
import io.ehdev.conrad.model.version.VersionGroupCapture;

import java.util.Arrays;

public class VersionCommitVersionBumper implements CommitVersionBumper, VersionGroupCapture {

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
