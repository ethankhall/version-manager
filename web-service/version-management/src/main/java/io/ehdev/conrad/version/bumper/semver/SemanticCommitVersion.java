package io.ehdev.conrad.version.bumper.semver;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionGroup;
import io.ehdev.conrad.version.commit.DefaultCommitVersionGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SemanticCommitVersion implements CommitVersion<Integer> {
    private final static int[] EMPTY_INT_ARRAY = new int[] {};

    private final int[] versionList;
    private final String postFix;

    public SemanticCommitVersion(int[] versionList, String postFix) {
        this.versionList = Arrays.copyOf(versionList, Math.max(3, versionList.length));
        this.postFix = postFix;
    }

    public static SemanticCommitVersion parse(String version) {
        String postfix = null;
        String numberString = version;
        if (version.contains("-")) {
            int indexOfDash = version.indexOf('-');
            postfix = version.substring(indexOfDash + 1);
            numberString = version.substring(0, indexOfDash);
        }

        String[] splitVersion = numberString.split("\\.");
        int[] versionList = Arrays.copyOf(EMPTY_INT_ARRAY, splitVersion.length);
        for (int i = 0; i < Math.min(splitVersion.length, versionList.length); i++) {
            versionList[i] = Integer.parseInt(splitVersion[i]);
        }

        return new SemanticCommitVersion(versionList, postfix);
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

    public int[] getVersionList() {
        return versionList;
    }

    @Override
    public Integer getGroup(CommitVersionGroup versionGroup) {
        if (versionList.length <= versionGroup.getGroup()) {
            return null;
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
        SemanticCommitVersion that = (SemanticCommitVersion) o;
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
    public int compareTo(@NotNull CommitVersion obj) {
        if(!(obj instanceof SemanticCommitVersion)) {
            return -1;
        }
        SemanticCommitVersion other = (SemanticCommitVersion) obj;

        if (StringUtils.equals(toString(), other.toString())) {
            return 0;
        }

        int majorDelta = getDistance(DefaultCommitVersionGroup.majorVersion(), this, other);
        int minorDelta = getDistance(DefaultCommitVersionGroup.minorVersion(), this, other);
        int patchDelta = getDistance(DefaultCommitVersionGroup.patchVersion(), this, other);
        int buildDelta = getDistance(DefaultCommitVersionGroup.buildVersion(), this, other);
        int postfixDelta = StringUtils.trimToEmpty(getPostFix()).compareTo(StringUtils.trimToEmpty(other.getPostFix()));

        return (int) (majorDelta * Math.pow(10, 7) + minorDelta * Math.pow(10, 5) + patchDelta * Math.pow(10, 3) + buildDelta + postfixDelta);
    }

    private static int getDistance(CommitVersionGroup group, SemanticCommitVersion self, SemanticCommitVersion other) {
        return self.getGroup(group) - other.getGroup(group);
    }
}
