package io.ehdev.conrad.model.internal;

import io.ehdev.conrad.model.CommitVersionBumper;

public class StandardVersionGroupBump {

    private static final VersionCommitVersionBumper majorVersion = new VersionCommitVersionBumper(0);
    private static final VersionCommitVersionBumper minorVersion = new VersionCommitVersionBumper(1);
    private static final VersionCommitVersionBumper patchVersion = new VersionCommitVersionBumper(2);
    private static final VersionCommitVersionBumper buildVersion = new VersionCommitVersionBumper(3);
    private static final CommitVersionBumper snapshot = new SnapshotCommitVersionBumper("SNAPSHOT");

    public static VersionCommitVersionBumper majorVersion() {
        return majorVersion;
    }

    public static VersionCommitVersionBumper minorVersion() {
        return minorVersion;
    }

    public static VersionCommitVersionBumper patchVersion() {
        return patchVersion;
    }

    public static VersionCommitVersionBumper buildVersion() {
        return buildVersion;
    }

    public static CommitVersionBumper snapshot() {
        return snapshot;
    }
}
