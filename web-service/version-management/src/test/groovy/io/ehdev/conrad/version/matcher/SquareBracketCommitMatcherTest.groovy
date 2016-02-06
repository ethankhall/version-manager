package io.ehdev.conrad.version.matcher
import spock.lang.Specification

class SquareBracketCommitMatcherTest extends Specification {
    def 'can match based on random string'() {
        expect:
        new SquareBracketCommitMatcher('123', null).matches('asdfasdf[bump 123]asdfasd')
        new SquareBracketCommitMatcher('123', null).matches('asdfasdf[       bump 123   ]asdfasd')
        new SquareBracketCommitMatcher('123( version)?', null).matches('asdfasdf[bump 123 version]asdfasd')
        !new SquareBracketCommitMatcher('123', null).matches('asdfasdf[bump 124]asdfasd')
    }

    def 'will throw exception when empty string'() {
        when:
        new SquareBracketCommitMatcher('', null)

        then:
        thrown(SquareBracketCommitMatcher.InvalidSearchStringException)
    }
}
