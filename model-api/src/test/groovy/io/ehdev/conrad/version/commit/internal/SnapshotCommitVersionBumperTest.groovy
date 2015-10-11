package io.ehdev.conrad.version.commit.internal

import io.ehdev.conrad.version.commit.VersionFactory
import spock.lang.Specification

class SnapshotCommitVersionBumperTest extends Specification {

    def 'will update version'() {
        expect:
        VersionFactory.parse('1.5.7').bump(new SnapshotCommitVersionBumper('BETA')).toString() == '1.5.7-BETA'
    }
}
