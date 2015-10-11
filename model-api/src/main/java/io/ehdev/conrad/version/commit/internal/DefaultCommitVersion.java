package io.ehdev.conrad.version.commit.internal;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.CommitVersionGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DefaultCommitVersion implements CommitVersion {
    final int[] versionList;
    final String postFix;

    public DefaultCommitVersion(int[] versionList, String postFix) {
        this.versionList = Arrays.copyOf(versionList, Math.max(3, versionList.length));
        this.postFix = postFix;
    }

    public DefaultCommitVersion(int major, int minor, int patch, String postFix) {
        this(new int[]{major, minor, patch}, postFix);
    }

    public DefaultCommitVersion(int major, int minor, int patch) {
        this(new int[]{major, minor, patch}, null);
    }

    @Override
    public CommitVersion bump(CommitVersionBumper commitVersionBumper) {
        return commitVersionBumper.bump(versionList, postFix);
    }

    @Override
    public String toVersionString() {
        String stringPostfix = (postFix != null ? '-' + postFix : "");
        return StringUtils.join(Arrays.copyOf(versionList, Math.max(3, versionList.length)), '.') + stringPostfix;
    }

    @Override
    public List<Integer> getVersionGroup() {
        return Arrays.asList(ArrayUtils.toObject(versionList));
    }

    @Override
    public Integer getGroup(CommitVersionGroup versionGroup) {
        if (versionList.length <= versionGroup.getGroup()) {
            return 0;
        } else {
            return versionList[versionGroup.getGroup()];
        }
    }

    @Override
    public String getPostFix() {
        return postFix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultCommitVersion that = (DefaultCommitVersion) o;
        return Arrays.equals(versionList, that.versionList) && Objects.equals(postFix, that.postFix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(versionList), postFix);
    }

    @Override
    public String toString() {
        return toVersionString();
    }

    @Override
    public int compareTo(CommitVersion other) {
        if (StringUtils.equals(toString(), other.toString())) {
            return 0;
        }

        int majorDelta = getGroup(StandardVersionGroupBump.majorVersion()) - other.getGroup(StandardVersionGroupBump.majorVersion());
        int minorDelta = getGroup(StandardVersionGroupBump.minorVersion()) - other.getGroup(StandardVersionGroupBump.minorVersion());
        int patchDelta = getGroup(StandardVersionGroupBump.patchVersion()) - other.getGroup(StandardVersionGroupBump.patchVersion());
        int buildDelta = getGroup(StandardVersionGroupBump.buildVersion()) - other.getGroup(StandardVersionGroupBump.buildVersion());
        int postfixDelta = StringUtils.trimToEmpty(getPostFix()).compareTo(StringUtils.trimToEmpty(other.getPostFix()));

        return (int) (majorDelta * Math.pow(10, 7) + minorDelta * Math.pow(10, 5) + patchDelta * Math.pow(10, 3) + buildDelta + postfixDelta);
    }
}
