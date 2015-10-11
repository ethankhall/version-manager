package io.ehdev.version.service
import io.ehdev.version.ApiTestConfiguration
import io.ehdev.version.TestDataUtil
import io.ehdev.version.model.repository.CommitModelRepository
import io.ehdev.version.model.repository.ScmMetaDataRepository
import io.ehdev.version.model.repository.VersionBumperRepository
import io.ehdev.version.service.model.VersionCreation
import io.ehdev.version.service.model.VersionSearch
import io.ehdev.version.service.version.VersionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@Rollback(true)
@ContextConfiguration(classes = [ApiTestConfiguration.class], loader = SpringApplicationContextLoader.class)
class VersionServiceIntegrationTest extends Specification {

    @Autowired
    VersionService versionService

    @Autowired
    VersionBumperRepository versionBumperRepository

    @Autowired
    ScmMetaDataRepository scmMetaDataRepository

    @Autowired
    CommitModelRepository commitRepository

    TestDataUtil testDataUtil

    def setup() {
        testDataUtil = new TestDataUtil(versionBumperRepository, scmMetaDataRepository, commitRepository)
    }

    def 'can get next version, when commit is #commitId'() {
        when:
        def receipt = testDataUtil.createCommitWithNoChildren()
        def version = versionService.getVersionForRepo(new VersionSearch(receipt.scmMetaDataModel.uuid, [receipt.commits[0].commitId]))

        then:
        noExceptionThrown()
        version.getVersionString() == '1.2.4-SNAPSHOT'

        when:
        receipt = testDataUtil.createCommitWithNext()
        version = versionService.getVersionForRepo(new VersionSearch(receipt.scmMetaDataModel.uuid, [receipt.commits[0].commitId]))
        def children = versionService.getVersionForRepo(new VersionSearch(receipt.scmMetaDataModel.uuid, [receipt.commits[1].commitId]))

        then:
        noExceptionThrown()
        version.getVersionString() == '1.2.4-SNAPSHOT'
        children.getVersionString() == '1.2.5-SNAPSHOT'

        when:
        receipt = testDataUtil.createWithNextAndBuildCommits()
        version = versionService.getVersionForRepo(new VersionSearch(receipt.scmMetaDataModel.uuid, [receipt.commits[0].commitId]))
        children = versionService.getVersionForRepo(new VersionSearch(receipt.scmMetaDataModel.uuid, [receipt.commits[1].commitId]))
        def build = versionService.getVersionForRepo(new VersionSearch(receipt.scmMetaDataModel.uuid, [receipt.commits[2].commitId]))

        then:
        noExceptionThrown()
        version.getVersionString() == '1.2.4-SNAPSHOT'
        children.getVersionString() == '1.2.5-SNAPSHOT'
        build.getVersionString() == '1.2.3.2-SNAPSHOT'
    }

    def 'will claim good versions'() {
        setup:
        def receipt = testDataUtil.createCommitWithNext()

        when:
        def creation = new VersionCreation()
        creation.setParentCommitId(receipt.commits[0].commitId)
        creation.setRepoId(receipt.scmMetaDataModel.uuid)
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
