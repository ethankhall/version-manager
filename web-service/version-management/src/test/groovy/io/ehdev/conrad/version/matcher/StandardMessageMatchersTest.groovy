package io.ehdev.conrad.version.matcher

import io.ehdev.conrad.version.matcher.internal.StandardMessageMatchers
import spock.lang.Specification

class StandardMessageMatchersTest extends Specification {

    def 'make test complete'() {
        expect:
        new StandardMessageMatchers(factory) != null
        new StandardMessageMatchers.SquareBracket() != null
    }
}
