package io.ehdev.version.update.matcher


import io.ehdev.version.commit.VersionGroup
import spock.lang.Specification
import spock.lang.Unroll

class SquareBracketMatcherTest extends Specification {

    def 'can match based on random string'() {
        expect:
        new SquareBracketMatcher('123').matches('asdfasdf[123]asdfasd')
        !new SquareBracketMatcher('123').matches('asdfasdf[124]asdfasd')
    }

    @Unroll
    def 'can match #version'() {
        setup:
        def matcher = new SquareBracketMatcher(version)

        expect:
        matcher.matches("[bump ${version.name()}]")
        matcher.matches("[bump ${version.name().toLowerCase()}]")

        where:
        version << Arrays.asList(VersionGroup.values())

    }
}
