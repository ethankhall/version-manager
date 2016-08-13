package tech.crom.database.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.crom.config.ClockConfig
import tech.crom.config.DatabaseConfig
import tech.crom.database.api.CommitManager
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.database.api.VersionBumperManager
import tech.crom.database.config.CromDoaConfig
import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.CommitIdContainer

@Transactional
@ContextConfiguration(classes = [DatabaseConfig, ClockConfig, CromDoaConfig], loader = SpringApplicationContextLoader.class)
class ManagerResourceIntegrationTest extends Specification {

    @Autowired
    CommitManager commitManager

    @Autowired
    ProjectManager projectManager

    @Autowired
    RepoManager repoManager

    @Autowired
    VersionBumperManager versionBumperManager

    def 'test crud'() {
        when:
        def project = projectManager.createProject('newProject')

        then:
        projectManager.findProject(project.projectName) == project
        projectManager.findProject(project.projectUid) == project

        when:
        def bumper = versionBumperManager.findBumper('semver')

        then:
        bumper != null

        when:
        def repo1 = repoManager.createRepo(project, 'repo1', bumper, null, "", true)

        then:
        repoManager.findRepo(repo1.repoUid) == repo1
        repoManager.findRepo(project, repo1.repoName) == repo1

        when:
        def repo2 = repoManager.createRepo(project, 'repo2', bumper, null, "", true)

        then:
        repoManager.findRepo(repo2.repoUid) == repo2
        repoManager.findRepo(project, repo2.repoName) == repo2

        when:
        def repos = repoManager.findRepo(project)

        then:
        repos as Set == [repo1, repo2] as Set

        when:
        repoManager.deleteRepo(repo2)

        then:
        !repoManager.findRepo(repo2.repoUid)
        !repoManager.findRepo(project, repo2.repoName)

        expect:
        commitManager.findAllCommits(repo1).isEmpty()

        when:
        def commit1 = commitManager.createCommit(repo1, new CommitDetails.RealizedCommit('1', '1.0.0', null), [])

        then:
        commit1
        commit1.version.versionString == '1.0.0'
        commitManager.findCommit(repo1, new CommitIdContainer(commit1.commitId)) == commit1

        when:
        def commit2 = commitManager.createCommit(repo1,
            new CommitDetails.RealizedCommit('2', '1.0.1', null),
            [new CommitIdContainer('1')])

        then:
        commit2
        commit2.version.versionString == '1.0.1'
        commitManager.findCommit(repo1, new CommitIdContainer(commit2.commitId)) == commit2

        expect:
        commitManager.findAllCommits(repo1) as Set == [commit1, commit2] as Set
    }
}
