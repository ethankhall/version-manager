package io.ehdev.conrad.version.bumper.atomic


import spock.lang.Specification

class AtomicNumberBumperTest extends Specification {

    def bumper = new AtomicNumberBumper()

    def 'will strip everything but major version'() {
        expect:
        bumper.createDefaultNextVersion('1.2.3').toVersionString() == '2'
        bumper.createDefaultNextVersion('2.2.3-FOO').toVersionString() == '3'
    }
}
