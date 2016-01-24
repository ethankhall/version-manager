package io.ehdev.conrad.database.api.internal

import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import io.ehdev.conrad.database.impl.bumper.VersionBumperModel
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository
import io.ehdev.conrad.model.internal.ApiCommit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultRepoManagementApiTest extends Specification {

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
        repoManagementApi.createCommit('project', 'newRepo', new ApiCommit('1'), null)
        def foundCommit = repoManagementApi.findLatestCommit('project', 'newRepo', [new ApiCommit('1')])

        then:
        foundCommit.isPresent()
        foundCommit.get().commitId == '1'

        when:
        foundCommit = repoManagementApi.findCommit('project', 'newRepo', new ApiCommit('1'))

        then:
        foundCommit.isPresent()
        foundCommit.get().commitId == '1'

        then:
        repoManagementApi.getAll().size() == 1

        when:
        def commits = repoManagementApi.findAllCommits('project', 'newRepo')

        then:
        commits.size() == 1
    }
}
