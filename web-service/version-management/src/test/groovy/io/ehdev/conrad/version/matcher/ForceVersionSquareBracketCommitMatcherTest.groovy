package io.ehdev.conrad.version.matcher

import io.ehdev.conrad.version.commit.VersionFactory
import io.ehdev.conrad.version.matcher.internal.ForceVersionSquareBracketCommitMatcher
import spock.lang.Specification
import spock.lang.Unroll

class ForceVersionSquareBracketCommitMatcherTest extends Specification {
    def 'can match based on random string'() {
        expect:
        new ForceVersionSquareBracketCommitMatcher().matches('asdfasdf[force version 123]asdfasd')
        new ForceVersionSquareBracketCommitMatcher().matches('asdfasdf[ force version 2.3.4.4.5.6.7.8.9 ]asdfasd')
        !new ForceVersionSquareBracketCommitMatcher().matches('asdfasdf[bump 124]asdfasd')
    }

    @Unroll
    def 'gives correct version for #version'() {
        def matcher = new ForceVersionSquareBracketCommitMatcher()

        expect:
        matcher.matches("asdfasdf[force version ${version.toVersionString()}]asdfasd")
        matcher.getBumper().bump(new int[1], 'foo') == version

        where:
        version                           | _
        VersionFactory.parse('1.2.3.4.5') | _
        VersionFactory.parse('1234')      | _
    }
}
