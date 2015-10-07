package io.ehdev.version.model.commit.internal;

import io.ehdev.version.model.commit.CommitVersion;
import io.ehdev.version.model.commit.VersionGroup;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;


public class DefaultCommitVersion implements CommitVersion {
    final int[] versionList;
    final String postFix;

    private DefaultCommitVersion(int[] versionList, String postFix) {
        this.versionList = Arrays.copyOf(versionList, 4);
        this.postFix = postFix;
    }

    public DefaultCommitVersion(int major, int minor, int patch, int build, String postFix) {
        this(new int[]{major, minor, patch, build}, postFix);
    }

    public DefaultCommitVersion(int major, int minor, int patch, int build) {
        this(new int[]{major, minor, patch, build}, null);
    }

    public DefaultCommitVersion(int major, int minor, int patch) {
        this(major, minor, patch, 0, null);
    }

    @Override
    public int getMajor() {
        return getGroup(VersionGroup.MAJOR);
    }

    @Override
    public int getMinor() {
        return getGroup(VersionGroup.MINOR);
    }

    @Override
    public int getPatch() {
        return getGroup(VersionGroup.PATCH);
    }

    @Override
    public int getBuild() {
        return getGroup(VersionGroup.BUILD);
    }

    @Override
    public String getPostFix() {
        return postFix;
    }

    @Override
    public int getGroup(VersionGroup versionGroup) {
        return versionList[versionGroup.getGroupNumber()];
    }

    @Override
    public CommitVersion bump(VersionGroup versionGroup) {
        int[] nextVersionList = Arrays.copyOf(versionList, versionList.length);
        //Set the lower digits to 0
        for (int i = versionGroup.getGroupNumber() + 1; i < versionList.length; i++) {
            nextVersionList[i] = 0;
        }

        //Bump the version
        nextVersionList[versionGroup.getGroupNumber()]++;

        return new DefaultCommitVersion(nextVersionList, null);
    }

    @Override
    public CommitVersion bumpBuild() {
        return bump(VersionGroup.BUILD);
    }

    @Override
    public CommitVersion bumpPatch() {

        return bump(VersionGroup.PATCH);
    }

    @Override
    public CommitVersion bumpMinor() {
        return bump(VersionGroup.MINOR);
    }

    @Override
    public CommitVersion bumpMajor() {
        return bump(VersionGroup.MAJOR);
    }

    @Override
    public CommitVersion withPostfix(String postfix) {
        return new DefaultCommitVersion(getMajor(), getMinor(), getPatch(), getBuild(), postfix);
    }

    @Override
    public CommitVersion asSnapshot() {
        return withPostfix("SNAPSHOT");
    }

    @Override
    public String toVersionString() {
        String stringPostfix = (postFix != null ? '-' + postFix : "");
        if (versionList[VersionGroup.BUILD.getGroupNumber()] == 0) {
            return String.format("%d.%d.%d%s", getMajor(), getMinor(), getPatch(), stringPostfix);
        } else {
            return String.format("%d.%d.%d.%d%s", getMajor(), getMinor(), getPatch(), getBuild(), stringPostfix);
        }
    }

    public static CommitVersion parse(String version) {
        int[] versionList = new int[]{0, 0, 0, 0};
        String postfix = null;
        String numberString = version;
        if (version.contains("-")) {
            int indexOfDash = version.indexOf('-');
            postfix = version.substring(indexOfDash + 1);
            numberString = version.substring(0, indexOfDash);
        }

        String[] splitVersion = numberString.split("\\.");
        for (int i = 0; i < Math.min(splitVersion.length, versionList.length); i++) {
            versionList[i] = Integer.parseInt(splitVersion[i]);
        }

        return new DefaultCommitVersion(versionList, postfix);
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
        if(StringUtils.equals(toString(), other.toString())) {
            return 0;
        }

        int majorDelta = getMajor() - other.getMajor();
        int minorDelta = getMinor() - other.getMinor();
        int patchDelta = getPatch() - other.getPatch();
        int buildDelta = getBuild() - other.getBuild();
        int postfixDelta = StringUtils.trimToEmpty(getPostFix()).compareTo(StringUtils.trimToEmpty(other.getPostFix()));

        return (int)(majorDelta * Math.pow(10, 7) + minorDelta * Math.pow(10, 5) + patchDelta * Math.pow(10, 3) + buildDelta + postfixDelta);
    }
}
