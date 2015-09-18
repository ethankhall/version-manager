package io.ehdev.version.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class RepositoryVersion {
    final int major;
    final int minor;
    final int patch;
    final String postFix;

    public RepositoryVersion(int major, int minor, int patch, String postFix) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.postFix = postFix;
    }

    public RepositoryVersion(int major, int minor, int patch) {
        this(major, minor, patch, null);
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getPostFix() {
        return postFix;
    }

    public RepositoryVersion bumpPatch() {
        return new RepositoryVersion(major, minor, patch + 1, postFix);
    }

    public RepositoryVersion bumpMinor() {
        return new RepositoryVersion(major, minor + 1, 0, postFix);
    }

    public RepositoryVersion bumpMajor() {
        return new RepositoryVersion(major + 1, 0, 0, postFix);
    }

    public RepositoryVersion withPostfix(String postfix) {
        return new RepositoryVersion(major, minor, patch, postfix);
    }

    public RepositoryVersion asSnapshot() {
        return withPostfix("SNAPSHOT");
    }

    public static RepositoryVersion parse(String version) {
        String postfix = null;
        String numberString = version;
        if (version.contains("-")) {
            int indexOfDash = version.indexOf('-');
            postfix = version.substring(indexOfDash + 1);
            numberString = version.substring(0, indexOfDash);
        }

        List<String> versionGroups = Arrays.asList(numberString.split("\\."));
        List<Integer> versionInts = versionGroups.stream().map(Integer::parseInt).collect(Collectors.toList());
        if (versionInts.size() < 3) {
            for (int i = versionInts.size(); i < 3; i++) {
                versionInts.add(0);
            }
        }
        return new RepositoryVersion(versionInts.get(0), versionInts.get(1), versionInts.get(2), postfix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryVersion that = (RepositoryVersion) o;
        return Objects.equals(major, that.major) &&
            Objects.equals(minor, that.minor) &&
            Objects.equals(patch, that.patch) &&
            Objects.equals(postFix, that.postFix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, postFix);
    }

    @Override
    public String toString() {
        if(StringUtils.isBlank(postFix)) {
            return String.format("%d.%d.%d", major, minor, patch);
        } else {
            return String.format("%d.%d.%d-%s", major, minor, patch, postFix);
        }
    }


}
