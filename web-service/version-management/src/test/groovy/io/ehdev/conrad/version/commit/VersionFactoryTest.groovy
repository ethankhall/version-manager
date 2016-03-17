package io.ehdev.conrad.version.commit
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.version.bumper.semver.StandardVersionGroupBump
import io.ehdev.conrad.version.commit.internal.DefaultCommitVersion
import spock.lang.Specification
import spock.lang.Unroll

class VersionFactoryTest extends Specification {

    @Unroll
    def 'can create repository version with #version'() {
        expect:
        VersionFactory.parse(version) == expected

        where:
        version          | expected
        "1"              | new DefaultCommitVersion(1, 0, 0)
        "1.2"            | new DefaultCommitVersion(1, 2, 0)
        "1.2.3"          | new DefaultCommitVersion(1, 2, 3)
        "1.2.3.4"        | new DefaultCommitVersion([1, 2, 3, 4] as int[], null)
        "1.2.3-SNAPSHOT" | new DefaultCommitVersion(1, 2, 3, 'SNAPSHOT')
        "1-SNAPSHOT"     | new DefaultCommitVersion(1, 0, 0, 'SNAPSHOT')
    }

    def 'parsing check'() {
        setup:
        def version = VersionFactory.parse("1.2.3-BETA")

        expect:
        version.getGroup(StandardVersionGroupBump.majorVersion()) == 1
        version.getGroup(StandardVersionGroupBump.minorVersion()) == 2
        version.getGroup(StandardVersionGroupBump.patchVersion()) == 3
        version.getGroup(StandardVersionGroupBump.buildVersion()) == 0
        version.getPostFix() == 'BETA'
        version.toString() == '1.2.3-BETA'
    }

    def 'can parse commit model'() {
        expect:
        VersionFactory.parseApiModel(new ApiCommitModel('commitId', '1.2.3.4.5.6.7.8.9.0')).toVersionString() == '1.2.3.4.5.6.7.8.9.0'
        VersionFactory.parseApiModel(null as ApiCommitModel)
    }
}
