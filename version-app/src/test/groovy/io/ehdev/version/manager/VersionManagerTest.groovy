package io.ehdev.version.manager
import io.ehdev.version.ApiTestConfiguration
import io.ehdev.version.TestDataUtil
import io.ehdev.version.model.commit.RepositoryCommit
import io.ehdev.version.model.commit.internal.DefaultCommitDetails
import io.ehdev.version.model.repository.CommitModelRepository
import io.ehdev.version.model.repository.ScmMetaDataRepository
import io.ehdev.version.model.repository.VersionBumperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

import static io.ehdev.version.TestDataUtil.createNewCommit

@Transactional
@Rollback(true)
@ContextConfiguration(classes = [ApiTestConfiguration.class], loader = SpringApplicationContextLoader.class)
public class VersionManagerTest extends Specification {

    @Autowired
    private CommitModelRepository commitRepository;

    @Autowired
    private ScmMetaDataRepository scmMetaDataRepository;

    @Autowired
    private VersionBumperRepository versionBumperRepository;

    @Autowired
    private VersionBumperManager versionBumperManager

    private VersionManager versionManager;

    private TestDataUtil testDataUtil;

    def setup() {
        versionManager = new VersionManager(commitRepository, scmMetaDataRepository, versionBumperManager);
        testDataUtil = new TestDataUtil(versionBumperRepository, scmMetaDataRepository, commitRepository)
    }

    def 'will create next commit, when no children commits are there'() {
        setup:
        def testDataReceipt = testDataUtil.createCommitWithNoChildren()
        DefaultCommitDetails childCommit = createNewCommit(testDataReceipt)

        when:
        RepositoryCommit claimedVersion = versionManager.claimVersion(childCommit);
        RepositoryCommit parentCommit = commitRepository.findByCommitIdAndRepoId(testDataReceipt.commits.first().commitId, testDataReceipt.getScmMetaDataModel().getUuidAsUUID())

        then:
        claimedVersion != null
        claimedVersion.version.toString() == '1.2.4'
        parentCommit.getNextCommit() == claimedVersion
    }

    def 'will create bump commit, when next child exists'() {
        setup:
        def testDataReceipt = testDataUtil.createCommitWithNext()
        DefaultCommitDetails details = createNewCommit(testDataReceipt)

        when:
        RepositoryCommit claimedVersion = versionManager.claimVersion(details);
        RepositoryCommit parentCommit = commitRepository.findByCommitIdAndRepoId(testDataReceipt.commits.first().commitId, testDataReceipt.getScmMetaDataModel().getUuidAsUUID())
        RepositoryCommit nextCommit = commitRepository.findByCommitIdAndRepoId(testDataReceipt.commits.last().commitId, testDataReceipt.getScmMetaDataModel().getUuidAsUUID())

        then:
        claimedVersion != null
        claimedVersion.version.toString() == '1.2.3.1'
        parentCommit.getBugfixCommit() == claimedVersion
        parentCommit.getNextCommit() == nextCommit
    }
}
