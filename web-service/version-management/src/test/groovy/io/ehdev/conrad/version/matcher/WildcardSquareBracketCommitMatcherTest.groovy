package io.ehdev.conrad.version.matcher


import io.ehdev.conrad.version.bumper.semver.SemanticCommitVersion
import io.ehdev.conrad.version.bumper.semver.SemanticCommitVersionFactory
import io.ehdev.conrad.version.commit.details.CommitDetailsFactory
import io.ehdev.conrad.version.matcher.internal.WildcardSquareBracketCommitMatcher
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class WildcardSquareBracketCommitMatcherTest extends Specification {

    @Shared
    def version = SemanticCommitVersion.parse('1.2.3')

    @Unroll
    def 'can bump group #groupNumber and makes #expectedVersion'() {
        when:
        def matcher = new WildcardSquareBracketCommitMatcher(new SemanticCommitVersionFactory())
        def details = CommitDetailsFactory.createCommitDetails('123', "some message \n[bump group ${groupNumber}]\nfoo")

        then:
        details.messageContains(matcher)
        matcher.getBumper().bump(version).toString() == expectedVersion

        where:
        groupNumber || expectedVersion
        1           || '2.0.0'
        2           || '1.3.0'
        3           || '1.2.4'
        4           || '1.2.3.1'
        5           || '1.2.3.0.1'
        6           || '1.2.3.0.0.1'
    }

    def 'throws exception'() {
        when:
        new WildcardSquareBracketCommitMatcher().getBumper()

        then:
        thrown(WildcardSquareBracketCommitMatcher.GroupNotFoundException)
    }
}
