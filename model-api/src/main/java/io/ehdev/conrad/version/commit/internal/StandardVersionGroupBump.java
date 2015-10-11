package io.ehdev.conrad.version.commit.internal;

import io.ehdev.conrad.version.commit.CommitVersionBumper;

public class StandardVersionGroupBump {

    private static final VersionCommitBumper majorVersion = new VersionCommitBumper(0);
    private static final VersionCommitBumper minorVersion = new VersionCommitBumper(1);
    private static final VersionCommitBumper patchVersion = new VersionCommitBumper(2);
    private static final VersionCommitBumper buildVersion = new VersionCommitBumper(3);
    private static final CommitVersionBumper snapshot = new SnapshotCommitVersionBumper("SNAPSHOT");

    public static VersionCommitBumper majorVersion() {
        return majorVersion;
    }

    public static VersionCommitBumper minorVersion() {
        return minorVersion;
    }

    public static VersionCommitBumper patchVersion() {
        return patchVersion;
    }

    public static VersionCommitBumper buildVersion() {
        return buildVersion;
    }

    public static CommitVersionBumper snapshot() {
        return snapshot;
    }
}
