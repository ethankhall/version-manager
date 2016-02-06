package io.ehdev.conrad.database.api.internal
import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.db.tables.daos.VersionBumpersDao
import io.ehdev.conrad.db.tables.pojos.VersionBumpers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Rollback
@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultRepoManagementApiIntegrationTest extends Specification {

    @Autowired
    DefaultRepoManagementApi repoManagementApi

    @Autowired
    DefaultProjectManagementApi projectManagementApi

    @Autowired
    VersionBumpersDao versionBumpersDao

    def setup() {
        projectManagementApi.createProject("project")
        versionBumpersDao.insert(new VersionBumpers(null, 'semver', Object.name, 'some description'))
    }

    def 'basic workflow'() {
        def repoModel = new ApiRepoModel('project', 'newRepo')
        when:
        def repo = repoManagementApi.createRepo(repoModel, 'semver', 'url')

        then:
        repo != null

        when:
        repoManagementApi.createCommit(repoModel, new ApiCommitModel('1', '1.2.3'), null)
        def foundCommit = repoManagementApi.findLatestCommit(repoModel, [new ApiCommitModel('1')])

        then:
        foundCommit.isPresent()
        foundCommit.get().commitId == '1'

        when:
        foundCommit = repoManagementApi.findCommit(repoModel, '1')

        then:
        foundCommit.isPresent()
        foundCommit.get().commitId == '1'

        then:
        repoManagementApi.getAllRepos().size() == 1

        when:
        def commits = repoManagementApi.findAllCommits(repoModel)

        then:
        commits.size() == 1
        commits[0].commitId == '1'

        when:
        repoManagementApi.createCommit(repoModel, new ApiCommitModel('2', '1.3.4'), new ApiCommitModel('1'))
        def allCommits = repoManagementApi.findAllCommits(repoModel)

        then:
        allCommits.size() == 2
        allCommits[0].commitId == '1'
        allCommits[1].commitId == '2'

        when:
        allCommits = repoManagementApi.findLatestCommit(repoModel, [new ApiCommitModel('2'), new ApiCommitModel('1')])

        then:
        allCommits.isPresent()
        allCommits.get().commitId == '2'
    }
}
