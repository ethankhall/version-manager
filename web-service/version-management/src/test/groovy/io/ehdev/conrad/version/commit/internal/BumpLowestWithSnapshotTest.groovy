package io.ehdev.conrad.version.commit.internal

import spock.lang.Specification

class BumpLowestWithSnapshotTest extends Specification {

    def 'run tests'() {
        expect:
        new BumpLowestWithSnapshot().bump([1,2,3,4,5] as int[], null).toVersionString() == '1.2.3.4.6-SNAPSHOT'
        new BumpLowestWithSnapshot().bump([1,2,3,4,5] as int[], 'foo').toVersionString() == '1.2.3.4.6-SNAPSHOT'
    }
}
