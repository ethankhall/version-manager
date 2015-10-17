package io.ehdev.conrad.backend.version.commit.internal

import io.ehdev.conrad.backend.version.commit.StandardVersionGroupBump
import io.ehdev.conrad.backend.version.commit.VersionFactory
import spock.lang.Specification

class DefaultCommitVersionTest extends Specification {

    def 'can bump different fields'() {
        expect:
        new DefaultCommitVersion(1, 0, 0).bump(StandardVersionGroupBump.patchVersion()).toString() == '1.0.1'
        new DefaultCommitVersion(1, 0, 0).bump(StandardVersionGroupBump.snapshot()).toString() == '1.0.0-SNAPSHOT'
        new DefaultCommitVersion(1, 0, 1).bump(StandardVersionGroupBump.minorVersion()).toString() == '1.1.0'
        new DefaultCommitVersion(1, 1, 1).bump(StandardVersionGroupBump.majorVersion()).toString() == '2.0.0'
        new DefaultCommitVersion(1, 1, 1).bump(StandardVersionGroupBump.buildVersion()).toString() == '1.1.1.1'
        new DefaultCommitVersion(1, 1, 1).bump(new SnapshotCommitVersionBumper("ALPHA1")).toString() == '1.1.1-ALPHA1'
    }

    def 'compare to works'() {
        expect:
        new DefaultCommitVersion(1, 0, 0).compareTo(new DefaultCommitVersion(1, 0, 0)) == 0
        new DefaultCommitVersion(1, 0, 1).compareTo(new DefaultCommitVersion(1, 0, 0)) > 1
        new DefaultCommitVersion(1, 0, 0).compareTo(new DefaultCommitVersion(1, 0, 1)) < -1
        new DefaultCommitVersion(0, 0, 0, "SNAPSHOT").compareTo(new DefaultCommitVersion(0, 0, 0, "ALPHA")) != 0
        VersionFactory.parse("1.2.3") > VersionFactory.parse("0.1.2")
        VersionFactory.parse("1.2.3") > VersionFactory.parse("1.2.2")
        VersionFactory.parse("0.1.2") < VersionFactory.parse("1.2.3")
        VersionFactory.parse("1.2.2") < VersionFactory.parse("1.2.3")
    }
}
