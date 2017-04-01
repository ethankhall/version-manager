package tech.crom.version.bumper.impl.semver.recognizer

import spock.lang.Specification
import spock.lang.Unroll

import static tech.crom.version.bumper.impl.TestUtils.createVersionDetails

class GroupBasedVersionCreatorTest extends Specification {

    @Unroll
    def 'expanding versions : #name'() {
        when:
        def versionCreator = new GroupBasedVersionCreator(createVersionDetails(), part)

        then:
        expected == versionCreator.nextVersion()

        where:
        name                              | part | expected
        'will update major version'       | 0    | '2.0.0'
        'will update minor version'       | 1    | '1.3.0'
        'will update patch version'       | 2    | '1.2.4'
        'will will create build group'    | 3    | '1.2.3.1'
        'will will create the 10th group' | 9    | '1.2.3.0.0.0.0.0.0.1'
    }
}
