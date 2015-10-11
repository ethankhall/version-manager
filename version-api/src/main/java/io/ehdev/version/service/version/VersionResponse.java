package io.ehdev.version.service.version;

import io.ehdev.version.model.commit.CommitVersion;

public class VersionResponse {

    final String repoId;
    final String versionString;
    final int major;
    final int minor;
    final int patch;
    final int build;
    final String metadata;

    public VersionResponse(String repoId, CommitVersion commitVersion) {
        this.repoId = repoId;
        this.versionString = commitVersion.toVersionString();
        this.major = commitVersion.getMajor();
        this.minor = commitVersion.getMinor();
        this.patch = commitVersion.getPatch();
        this.build = commitVersion.getBuild();
        this.metadata = commitVersion.getPostFix();
    }

    public String getRepoId() {
        return repoId;
    }

    public String getVersionString() {
        return versionString;
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

    public int getBuild() {
        return build;
    }

    public String getMetadata() {
        return metadata;
    }
}
