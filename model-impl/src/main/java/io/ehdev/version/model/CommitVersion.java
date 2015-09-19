package io.ehdev.version.model;

import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;


public class CommitVersion implements Comparable<CommitVersion> {
    final int[] versionList;
    final String postFix;

    private CommitVersion(int[] versionList, String postFix) {
        this.versionList = Arrays.copyOf(versionList, 4);
        this.postFix = postFix;
    }

    public CommitVersion(int major, int minor, int patch, int build, String postFix) {
        this(new int[]{major, minor, patch, build}, postFix);
    }

    public CommitVersion(int major, int minor, int patch, int build) {
        this(new int[]{major, minor, patch, build}, null);
    }

    public CommitVersion(int major, int minor, int patch) {
        this(major, minor, patch, 0, null);
    }

    public int getMajor() {
        return getGroup(VersionGroup.MAJOR);
    }

    public int getMinor() {
        return getGroup(VersionGroup.MINOR);
    }

    public int getPatch() {
        return getGroup(VersionGroup.PATCH);
    }

    public int getBuild() {
        return getGroup(VersionGroup.BUILD);
    }

    public String getPostFix() {
        return postFix;
    }

    public int getGroup(VersionGroup versionGroup) {
        return versionList[versionGroup.groupNumber];
    }

    protected CommitVersion bump(VersionGroup versionGroup) {
        int[] nextVersionList = Arrays.copyOf(versionList, versionList.length);
        //Set the lower digits to 0
        for (int i = versionGroup.getGroupNumber() + 1; i < versionList.length; i++) {
            nextVersionList[i] = 0;
        }

        //Bump the version
        nextVersionList[versionGroup.getGroupNumber()]++;

        return new CommitVersion(nextVersionList, null);
    }

    public CommitVersion bumpBuild() {
        return bump(VersionGroup.BUILD);
    }

    public CommitVersion bumpPatch() {

        return bump(VersionGroup.PATCH);
    }

    public CommitVersion bumpMinor() {
        return bump(VersionGroup.MINOR);
    }

    public CommitVersion bumpMajor() {
        return bump(VersionGroup.MAJOR);
    }

    public CommitVersion withPostfix(String postfix) {
        return new CommitVersion(getMajor(), getMinor(), getPatch(), getBuild(), postfix);
    }

    public CommitVersion asSnapshot() {
        return withPostfix("SNAPSHOT");
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

        return new CommitVersion(versionList, postfix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommitVersion that = (CommitVersion) o;
        return Arrays.equals(versionList, that.versionList) && Objects.equals(postFix, that.postFix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(versionList), postFix);
    }

    @Override
    public String toString() {
        String stringPostfix = (postFix != null ? '-' + postFix : "");
        if (versionList[VersionGroup.BUILD.groupNumber] == 0) {
            return String.format("%d.%d.%d%s", getMajor(), getMinor(), getPatch(), stringPostfix);
        } else {
            return String.format("%d.%d.%d.%d%s", getMajor(), getMinor(), getPatch(), getBuild(), stringPostfix);
        }
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
