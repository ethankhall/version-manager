package io.ehdev.conrad.version.bumper.semver


import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class SemverVersionCommitBumperTest extends Specification {

    @Shared
    SemanticCommitVersion commitVersion = SemanticCommitVersion.parse('1.2.3')

    @Unroll
    def 'version bump group can go to #depth giving #version'() {
        expect:
        new SemanticCommitBumperContainer.SemverVersionCommitBumper(depth).bump(commitVersion).toString() == version

        where:
        depth || version
        0     || '2.0.0'
        1     || '1.3.0'
        2     || '1.2.4'
        3     || '1.2.3.1'
        4     || '1.2.3.0.1'
        5     || '1.2.3.0.0.1'
    }
}
