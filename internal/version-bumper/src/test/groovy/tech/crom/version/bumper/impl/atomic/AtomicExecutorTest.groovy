package tech.crom.version.bumper.impl.atomic

import spock.lang.Specification
import spock.lang.Unroll
import tech.crom.model.commit.impl.RequestedCommit

import static tech.crom.version.bumper.impl.TestUtils.createVersionDetails

class AtomicExecutorTest extends Specification {

    @Unroll
    def 'an atomic bumper : #name'() {
        when:
        def atomicBumper = new AtomicVersionBumper()
        def commit = new RequestedCommit("abc", "asdfasf", null)
        def nextVersion = atomicBumper.calculateNextVersion(commit, lastVersion)

        then:
        'abc' == nextVersion.commitId
        expectedVersion == nextVersion.version.versionString

        where:
        name                                                     | expectedVersion | lastVersion
        'will produce a version from none'                       | '1'             | null
        'will produce the next version from existing'            | '14'            | createVersionDetails("13")
        'will produce the next version from existing, from list' | '2'             | createVersionDetails("1.3.4")
    }
}
