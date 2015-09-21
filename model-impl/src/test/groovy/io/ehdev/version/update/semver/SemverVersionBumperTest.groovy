package io.ehdev.version.update.semver
import io.ehdev.version.commit.RepositoryCommit
import io.ehdev.version.commit.VersionGroup
import io.ehdev.version.commit.internal.DefaultCommitDetails
import io.ehdev.version.commit.internal.DefaultCommitVersion
import io.ehdev.version.commit.RepositoryCommitBuilder
import io.ehdev.version.update.NextVersion
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SemverVersionBumperTest extends Specification {

    @Subject
    def semverBumper = new SemverVersionBumper()

    @Unroll
    def 'when claim is called, then \'#commitMessage\' will be of type #type'() {
        when:
        def details = new DefaultCommitDetails(commitMessage, null, null, null)

        then:
        semverBumper.findVersionGroupForBump(details) == type

        where:
        commitMessage         | type
        '[bump major]\n\nfoo' | VersionGroup.MAJOR
        '[bump minor]'        | VersionGroup.MINOR
        '[bump build]'        | VersionGroup.BUILD
        'this is a commit'    | null
    }

    def 'when there is no next version, then it will take the next location'() {
        setup:
        def parent = createParent()
        def details = new DefaultCommitDetails('this is a normal commit', 'as234', 'foo', null)

        when:
        def nextVersion = semverBumper.createNextVersion(parent, details)

        then:
        nextVersion.type == NextVersion.Type.NEXT
        nextVersion.commitVersion.toString() == '1.2.4'
    }

    def 'when there is a next version, then it will take the bugfix location'() {
        setup:
        def parent = createParent(true)
        def details = new DefaultCommitDetails('this is a normal commit', 'as234', 'foo', null)

        when:
        def nextVersion = semverBumper.createNextVersion(parent, details)

        then:
        nextVersion.type == NextVersion.Type.BUGFIX
        nextVersion.commitVersion.toString() == '1.2.3.1'
    }

    def 'when there next and bugfix are taken, it will throw'() {
        setup:
        def parent = createParent(true, true)
        def details = new DefaultCommitDetails('this is a normal commit', 'as234', 'foo', null)

        when:
        semverBumper.createNextVersion(parent, details)

        then:
        thrown(SemverVersionBumper.UnableToBumpVersionDueToFullCommitException)
    }

    RepositoryCommit createParent(boolean hasNextCommit = false, boolean hasBuildCommit = false) {
        def nextCommit = null;
        def bugfixCommit = null;
        if(hasNextCommit) {
            nextCommit = new RepositoryCommitBuilder('123', 'foo').withVersion(DefaultCommitVersion.parse('1.2.4')).build()
        }

        if(hasBuildCommit) {
            bugfixCommit = new RepositoryCommitBuilder('abc', 'foo').withVersion(DefaultCommitVersion.parse('1.2.3.1')).build()
        }
        return new RepositoryCommitBuilder('abcdef1234567890', 'foo')
            .withVersion(DefaultCommitVersion.parse('1.2.3'))
            .withNextCommit(nextCommit)
            .withBugfixCommit(bugfixCommit)
            .build()
    }

}
