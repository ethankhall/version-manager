package io.ehdev.conrad.backend.commit.matcher
import spock.lang.Specification

class SquareBracketCommitMatcherTest extends Specification {
    def 'can match based on random string'() {
        expect:
        new SquareBracketCommitMatcher('123', null).matches('asdfasdf[bump 123]asdfasd')
        !new SquareBracketCommitMatcher('123', null).matches('asdfasdf[bump 124]asdfasd')
    }
}
