package io.ehdev.conrad.version.matcher

import spock.lang.Specification

class StandardMessageMatchersTest extends Specification {

    def 'make test complete'() {
        expect:
        new StandardMessageMatchers() != null
        new StandardMessageMatchers.SquareBracket() != null
    }
}
