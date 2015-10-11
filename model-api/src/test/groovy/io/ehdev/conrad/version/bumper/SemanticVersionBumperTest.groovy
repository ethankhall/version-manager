package io.ehdev.conrad.version.bumper

import de.svenjacobs.loremipsum.LoremIpsum
import io.ehdev.conrad.commit.CommitDetails
import io.ehdev.conrad.commit.CommitDetailsFactory
import io.ehdev.conrad.version.commit.CommitVersion
import io.ehdev.conrad.version.commit.VersionFactory
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SemanticVersionBumperTest extends Specification {

    @Shared
    def ipsum = new LoremIpsum()

    @Shared
    CommitVersion commitVersion = VersionFactory.parse('1.2.3')

    @Subject
    SemanticVersionBumper semVerBumper = new SemanticVersionBumper()

    def 'will get default version if no bump is included'() {
        expect:
        semVerBumper.createNextVersion(commitVersion, commitDetails('some message')) == VersionFactory.parse('1.2.4')
    }

    @Unroll
    def 'message containing #search will give version #version'() {
        def details = commitDetails(search)
        expect:
        semVerBumper.createNextVersion(commitVersion, details) == VersionFactory.parse(version)

        where:
        search         | version
        '[bump patch]' | '1.2.4'
        '[bump build]' | '1.2.3.1'
        '[bump minor]' | '1.3.0'
        '[bump major]' | '2.0'

    }

    CommitDetails commitDetails(String message) {
        return CommitDetailsFactory.createCommitDetails('id', ipsum.getWords(30) + message + ipsum.getWords(12))
    }
}
