package io.ehdev.conrad.database.api.internal
import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import io.ehdev.conrad.database.impl.bumper.VersionBumperModel
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultRepoManagementApiIntegrationTest extends Specification {

    @Autowired
    DefaultRepoManagementApi repoManagementApi

    @Autowired
    DefaultProjectManagementApi projectManagementApi

    @Autowired
    VersionBumperModelRepository versionBumperModelRepository

    def setup() {
        projectManagementApi.createProject("project")
        versionBumperModelRepository.save(new VersionBumperModel(Object.name, 'some description', 'semver'))
    }

    def 'basic workflow'() {
        when:
        def repo = repoManagementApi.createRepo('project', 'newRepo', 'semver', 'url')

        then:
        repo != null

        when:
        repoManagementApi.createCommit('project', 'newRepo', new ApiFullCommitModel('1'), null)
        def foundCommit = repoManagementApi.findLatestCommit('project', 'newRepo', [new ApiFullCommitModel('1')])

        then:
        foundCommit.isPresent()
        foundCommit.get().commitId == '1'

        when:
        foundCommit = repoManagementApi.findCommit('project', 'newRepo', new ApiFullCommitModel('1'))

        then:
        foundCommit.isPresent()
        foundCommit.get().commitId == '1'

        then:
        repoManagementApi.getAll().size() == 1

        when:
        def commits = repoManagementApi.findAllCommits('project', 'newRepo')

        then:
        commits.size() == 1

        when:
        repoManagementApi.createCommit('project', 'newRepo', new ApiFullCommitModel('2'), [new ApiFullCommitModel('1')])
        def allCommits = repoManagementApi.findAllCommits('project', 'newRepo')

        then:
        allCommits.size() == 2
        allCommits[0].commitId == '2'
        allCommits[1].commitId == '1'

        when:
        allCommits = repoManagementApi.findLatestCommit('project', 'newRepo', [new ApiFullCommitModel('2'), new ApiFullCommitModel('1')])

        then:
        allCommits.isPresent()
        allCommits.get().commitId == '2'
    }
}