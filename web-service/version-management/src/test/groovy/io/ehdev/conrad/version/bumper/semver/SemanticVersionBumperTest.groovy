package io.ehdev.conrad.version.bumper.semver


import de.svenjacobs.loremipsum.LoremIpsum
import io.ehdev.conrad.version.commit.details.CommitDetails
import io.ehdev.conrad.version.commit.details.CommitDetailsFactory
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SemanticVersionBumperTest extends Specification {

    @Shared
    def ipsum = new LoremIpsum()

    @Subject
    SemanticVersionBumper semVerBumper = new SemanticVersionBumper()

    def 'will get default version if no bump is included'() {
        expect:
        semVerBumper.createNextVersion('1.2.3', commitDetails('some message')) == SemanticCommitVersion.parse('1.2.4')
    }

    @Unroll
    def 'message containing #search will give version #version'() {
        def details = commitDetails(search)
        def nextVersion = semVerBumper.createNextVersion('1.2.3', details)

        expect:
        nextVersion == SemanticCommitVersion.parse(version)
        nextVersion.toString() == version

        where:
        search                 | version
        '[bump build]'         | '1.2.3.1'
        '[bump build version]' | '1.2.3.1'
        '[bump patch]'         | '1.2.4'
        '[bump patch version]' | '1.2.4'
        '[bump minor]'         | '1.3.0'
        '[bump minor version]' | '1.3.0'
        '[bump major]'         | '2.0.0'
        '[bump major version]' | '2.0.0'
        '[bump group 4]'       | '1.2.3.1'
        '[bump group 3]'       | '1.2.4'
        '[bump group 2]'       | '1.3.0'
        '[bump group 1]'       | '2.0.0'

    }

    CommitDetails commitDetails(String message) {
        return CommitDetailsFactory.createCommitDetails('id', ipsum.getWords(30) + message + ipsum.getWords(12))
    }
}