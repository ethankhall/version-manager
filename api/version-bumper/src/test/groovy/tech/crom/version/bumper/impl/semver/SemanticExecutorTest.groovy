package tech.crom.version.bumper.impl.semver

import de.svenjacobs.loremipsum.LoremIpsum
import spock.lang.Specification
import spock.lang.Unroll
import tech.crom.model.commit.impl.RequestedCommit

import static tech.crom.version.bumper.impl.TestUtils.createVersionDetails

class SemanticExecutorTest extends Specification {

    def semanticVersionBumper = new SemanticVersionBumper()
    def commitModelCreator = { String message -> new RequestedCommit("123", message, null) }

    @Unroll
    def 'force version : #name'() {
        when:
        def commitModel = commitModelCreator("[force version 1.2.3.4]")
        def versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)

        then:
        assert versionModel
        "123" == versionModel.commitId
        "1.2.3.4" == versionModel.version.versionString

        where:
        name                                    | previousVersion
        'will handle no previous version found' | null
        'will not care about previous version'  | createVersionDetails()
    }

    @Unroll
    def 'group based matcher : #name'() {
        when:
        def commitModel = commitModelCreator(message)
        def versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)

        then:
        assert versionModel
        "123" == versionModel.commitId
        versionString == versionModel.version.versionString

        where:
        name                                         | message                | versionString | previousVersion
        'major version bumper - no previous version' | '[bump major version]' | '0.0.1'       | null
        'major version bumper - previous version'    | '[bump major version]' | '2.0.0'       | createVersionDetails()
        'minor version bumper - no previous version' | '[bump minor version]' | '0.0.1'       | null
        'minor version bumper - previous version'    | '[bump minor version]' | '1.3.0'       | createVersionDetails()
        'patch version bumper - no previous version' | '[bump patch version]' | '0.0.1'       | null
        'patch version bumper - previous version'    | '[bump patch version]' | '1.2.4'       | createVersionDetails()
        'build version bumper - no previous version' | '[bump build version]' | '0.0.1'       | null
        'build version bumper - previous version'    | '[bump build version]' | '1.2.3.1'     | createVersionDetails()
    }

    @Unroll
    def 'no version bump specified : #name'() {
        when:
        def commitModel = commitModelCreator(new LoremIpsum().getParagraphs(3))
        def versionModel = semanticVersionBumper.calculateNextVersion(commitModel, previousVersion)

        then:
        assert versionModel
        "123" == versionModel.commitId
        expectedVersion == versionModel.version.versionString

        then:
        where:
        name                                             | previousVersion        | expectedVersion
        'no previous version - it will use next version' | null                   | '0.0.1'
        'a previous version - it will use next version'  | createVersionDetails() | '1.2.4'
    }
}
