package io.ehdev.conrad.version.commit.internal;

import io.ehdev.conrad.version.commit.CommitVersionBumper;

public class StandardVersionGroupBump {

    private static final DefaultVersionCommitBumper majorVersion = new DefaultVersionCommitBumper(0);
    private static final DefaultVersionCommitBumper minorVersion = new DefaultVersionCommitBumper(1);
    private static final DefaultVersionCommitBumper patchVersion = new DefaultVersionCommitBumper(2);
    private static final DefaultVersionCommitBumper buildVersion = new DefaultVersionCommitBumper(3);
    private static final CommitVersionBumper snapshot = new SnapshotCommitVersionBumper("SNAPSHOT");

    public static DefaultVersionCommitBumper majorVersion() {
        return majorVersion;
    }

    public static DefaultVersionCommitBumper minorVersion() {
        return minorVersion;
    }

    public static DefaultVersionCommitBumper patchVersion() {
        return patchVersion;
    }

    public static DefaultVersionCommitBumper buildVersion() {
        return buildVersion;
    }

    public static CommitVersionBumper snapshot() {
        return snapshot;
    }
}
