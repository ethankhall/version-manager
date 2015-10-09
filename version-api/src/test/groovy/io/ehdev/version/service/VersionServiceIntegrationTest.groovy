package io.ehdev.version.service
import io.ehdev.version.ApiTestConfiguration
import io.ehdev.version.TestDataLoader
import io.ehdev.version.service.model.VersionCreation
import io.ehdev.version.service.model.VersionSearch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import javax.transaction.Transactional

@Transactional
@Rollback(true)
@ContextConfiguration(classes = [ApiTestConfiguration.class], loader = SpringApplicationContextLoader.class)
class VersionServiceIntegrationTest extends Specification {

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    VersionService versionService

    @Unroll
    def 'can get next version, when commit is #commitId'() {
        setup:
        testDataLoader.loadData()

        when:
        def version = versionService.getVersionForRepo(new VersionSearch(repo, [commitId]))

        then:
        noExceptionThrown()
        version.getVersionString() == expectedVersion

        where:
        commitId                             | repo          | expectedVersion
        TestDataLoader.NO_CHILDREN_COMMIT_ID | 'no-children' | '1.2.4-SNAPSHOT'
        TestDataLoader.PARENT_COMMIT_ID      | 'next'        | '1.2.4-SNAPSHOT'
        TestDataLoader.CHILD_COMMIT_ID       | 'next'        | '1.2.5-SNAPSHOT'
        TestDataLoader.BUGFIX_COMMIT_ID      | 'build'       | '1.2.3.2-SNAPSHOT'
    }

    def 'will claim good versions'() {
        setup:
        testDataLoader.loadData()

        when:
        def creation = new VersionCreation()
        creation.setParentCommitId(TestDataLoader.PARENT_COMMIT_ID)
        creation.setRepoId('next')
        creation.setCommitMessage('normal message')
        def commits = (0..9).collect {
            String newCommitId = 'thisCommit' + it
            creation.setCommitId(newCommitId)
            def revision = versionService.createNewCommitRevision(creation)
            creation.setParentCommitId(newCommitId)
            return revision
        }

        then:
        commits.size() == 10
        (0..9).each {
            assert commits[it].getVersionString() == '1.2.3.' + (it + 1)
        }

        when:
        creation.setCommitId('new bump')
        creation.setCommitMessage('this message has [bump minor] in it')
        def revision = versionService.createNewCommitRevision(creation)

        then:
        revision.getVersionString() == '1.2.4'
    }
}
