package io.ehdev.conrad.version.bumper.semver


import spock.lang.Specification

class PostfixCommitVersionBumperTest extends Specification {

    def 'will update version'() {
        expect:
        new SemanticCommitBumperContainer.PostfixCommitVersionBumper('BETA').bump(SemanticCommitVersion.parse('1.5.7')).toString() == '1.5.7-BETA'
    }

}
