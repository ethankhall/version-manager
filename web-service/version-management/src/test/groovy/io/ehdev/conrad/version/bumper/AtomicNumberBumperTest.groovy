package io.ehdev.conrad.version.bumper


import io.ehdev.conrad.version.commit.internal.DefaultCommitVersion
import spock.lang.Ignore
import spock.lang.Specification

class AtomicNumberBumperTest extends Specification {

    def bumper = new AtomicNumberBumper()

    @Ignore
    def 'will strip everything but major version'() {
        expect:
        bumper.createDefaultNextVersion(createVersion([1, 2, 3], null)).toVersionString() == '2'
        bumper.createDefaultNextVersion(createVersion([2, 2, 3], 'FOO')).toVersionString() == '3'
    }

    private static DefaultCommitVersion createVersion(ArrayList<Integer> integers, String postfix = null) {
        new DefaultCommitVersion(integers as int[], postfix)
    }
}
