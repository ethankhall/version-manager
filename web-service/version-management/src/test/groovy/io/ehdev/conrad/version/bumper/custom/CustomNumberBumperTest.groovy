package io.ehdev.conrad.version.bumper.custom


import io.ehdev.conrad.version.commit.details.DefaultCommitDetails
import spock.lang.Specification

class CustomNumberBumperTest extends Specification {

    def bumper = new CustomNumberBumper()

    CustomCommitVersionFactory factory = new CustomCommitVersionFactory()

    def 'requires a [set version foo]'() {
        expect:
        bumper.createNextVersion(null, new DefaultCommitDetails("1", '[set version 1.2.3]')).toVersionString() == '1.2.3'
    }

    def 'when not set, exception will be thrown'() {
        when:
        bumper.createNextVersion(null, new DefaultCommitDetails("1", '1.2.3'))

        then:
        thrown(CustomNumberBumper.VersionNotSpecifiedException)
    }
}
