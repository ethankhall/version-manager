package tech.crom.rest.model.version

import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

class BaseVersionResponseTest extends Specification {
    class ExampleBaseVersion extends BaseVersionResponse {
        ExampleBaseVersion(String commitId, String version, ZonedDateTime createdAt) {
            super(commitId, version, createdAt)
        }
    }

    @Unroll
    def 'create a simple object : #name'() {
        expect:
        new ExampleBaseVersion("abcdefg", version, ZonedDateTime.now()).version == version

        where:
        name                                              | version
        'should return all the version parts'             | '1.2.3'
        'should return all the version parts and postfix' | '1.2.3-NAME'
    }
}
