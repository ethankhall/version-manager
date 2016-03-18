package io.ehdev.conrad.version.bumper.semver


import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.version.commit.DefaultCommitVersionGroup
import spock.lang.Specification
import spock.lang.Unroll

class SemanticCommitVersionTest extends Specification {

    @Unroll
    def 'can create version with #version'() {
        expect:
        SemanticCommitVersion.parse(version) == expected

        where:
        version          | expected
        "1"              | createCommitVersion(1, 0, 0, null)
        "1.2"            | createCommitVersion(1, 2, 0)
        "1.2.3"          | createCommitVersion(1, 2, 3)
        "1.2.3.4"        | new SemanticCommitVersion([1, 2, 3, 4] as int[], null)
        "1.2.3-SNAPSHOT" | createCommitVersion(1, 2, 3, 'SNAPSHOT')
        "1-SNAPSHOT"     | createCommitVersion(1, 0, 0, 'SNAPSHOT')
    }

    def 'parsing check'() {
        setup:
        def version = SemanticCommitVersion.parse("1.2.3-BETA")

        expect:
        version.getGroup(DefaultCommitVersionGroup.majorVersion()) == 1
        version.getGroup(DefaultCommitVersionGroup.minorVersion()) == 2
        version.getGroup(DefaultCommitVersionGroup.patchVersion()) == 3
        version.getGroup(DefaultCommitVersionGroup.buildVersion()) == null
        version.getPostFix() == 'BETA'
        version.toString() == '1.2.3-BETA'
    }

    def 'can parse commit model'() {
        expect:
        SemanticCommitVersion.parse(new ApiCommitModel('commitId', '1.2.3.4.5.6.7.8.9.0')).toVersionString() == '1.2.3.4.5.6.7.8.9.0'
    }

    def createCommitVersion(int major, int minor, int patch, String postfix = null) {
        return new SemanticCommitVersion([major, minor, patch] as int[], postfix)
    }
}
