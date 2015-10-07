package io.ehdev.version.manager
import io.ehdev.version.IntegrationTestConfiguration
import io.ehdev.version.TestDataLoader
import io.ehdev.version.model.commit.RepositoryCommit
import io.ehdev.version.model.commit.internal.DefaultCommitDetails
import io.ehdev.version.model.repository.CommitModelRepository
import io.ehdev.version.model.repository.ScmMetaDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@Rollback(true)
@ContextConfiguration(classes = [IntegrationTestConfiguration.class], loader = SpringApplicationContextLoader.class)
public class VersionManagerTest extends Specification {

    @Autowired
    private TestDataLoader testDataLoader;

    @Autowired
    private CommitModelRepository commitRepository;

    @Autowired
    private ScmMetaDataRepository scmMetaDataRepository;

    @Autowired
    private VersionBumperManager versionBumperManager

    private VersionManager versionManager;

    def setup() {
        versionManager = new VersionManager(commitRepository, scmMetaDataRepository, versionBumperManager);
        testDataLoader.loadData();
    }

    def 'will create next commit, when no children commits are there'() {
        setup:
        DefaultCommitDetails details = new DefaultCommitDetails("message?",
            "child-sha",
            TestDataLoader.NO_CHILDREN_COMMIT_ID,
            'no-children');

        when:
        RepositoryCommit claimedVersion = versionManager.claimVersion(details);
        RepositoryCommit parentCommit = commitRepository.findByCommitIdAndRepoId(TestDataLoader.NO_CHILDREN_COMMIT_ID, 'no-children')

        then:
        claimedVersion != null
        claimedVersion.version.toString() == '1.2.4'
        parentCommit.getNextCommit() == claimedVersion
    }

    def 'will create bump commit, when next child exists'() {
        setup:
        DefaultCommitDetails details = new DefaultCommitDetails("message?",
            "child-sha",
            TestDataLoader.PARENT_COMMIT_ID,
            'next');

        when:
        RepositoryCommit claimedVersion = versionManager.claimVersion(details);
        RepositoryCommit parentCommit = commitRepository.findByCommitIdAndRepoId(TestDataLoader.PARENT_COMMIT_ID, 'next')
        RepositoryCommit nextCommit = commitRepository.findByCommitIdAndRepoId(TestDataLoader.CHILD_COMMIT_ID, 'next')

        then:
        claimedVersion != null
        claimedVersion.version.toString() == '1.2.3.1'
        parentCommit.getBugfixCommit() == claimedVersion
        parentCommit.getNextCommit() == nextCommit
    }
}
