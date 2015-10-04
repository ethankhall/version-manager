package io.ehdev.version.commit

import io.ehdev.version.commit.internal.DefaultCommitVersion
import spock.lang.Specification
import spock.lang.Unroll

class CommitVersionTest extends Specification {

    @Unroll
    def 'can create repository version with #version'() {
        expect:
        DefaultCommitVersion.parse(version) == expected

        where:
        version          | expected
        "1"              | new DefaultCommitVersion(1, 0, 0)
        "1.2"            | new DefaultCommitVersion(1, 2, 0)
        "1.2.3"          | new DefaultCommitVersion(1, 2, 3)
        "1.2.3.4"        | new DefaultCommitVersion(1, 2, 3, 4)
        "1.2.3-SNAPSHOT" | new DefaultCommitVersion(1, 2, 3, 0, 'SNAPSHOT')
        "1-SNAPSHOT"     | new DefaultCommitVersion(1, 0, 0, 0, 'SNAPSHOT')
    }

    def 'can bump to snapshot'() {
        expect:
        new DefaultCommitVersion(1, 0, 0).bumpPatch().toString() == '1.0.1'
        new DefaultCommitVersion(1, 0, 0).asSnapshot().toString() == '1.0.0-SNAPSHOT'
        new DefaultCommitVersion(1, 0, 1).bumpMinor().toString() == '1.1.0'
        new DefaultCommitVersion(1, 1, 1).bumpMajor().toString() == '2.0.0'
        new DefaultCommitVersion(1, 1, 1).bumpBuild().toString() == '1.1.1.1'
        new DefaultCommitVersion(1, 1, 1).withPostfix("ALPHA1").toString() == '1.1.1-ALPHA1'
    }

    def 'parsing check'() {
        setup:
        def version = DefaultCommitVersion.parse("1.2.3-BETA")

        expect:
        version.getMajor() == 1
        version.getMinor() == 2
        version.getPatch() == 3
        version.getBuild() == 0
        version.getPostFix() == 'BETA'
        version.toString() == '1.2.3-BETA'
    }

    def 'compare to works'() {
        expect:
        new DefaultCommitVersion(1, 0, 0).compareTo(new DefaultCommitVersion(1, 0, 0)) == 0
        new DefaultCommitVersion(1, 0, 1).compareTo(new DefaultCommitVersion(1, 0, 0)) > 1
        new DefaultCommitVersion(1, 0, 0).compareTo(new DefaultCommitVersion(1, 0, 1)) < -1
        new DefaultCommitVersion(0, 0, 0, 0, "SNAPSHOT").compareTo(new DefaultCommitVersion(0, 0, 0, 0, "ALPHA")) != 0
    }
}
