package io.ehdev.conrad.database.repository
import io.ehdev.conrad.backend.version.commit.VersionFactory
import io.ehdev.conrad.database.config.DatabaseConfiguration
import io.ehdev.conrad.database.model.VcsRepoModel
import io.ehdev.conrad.test.IntegrationTestGroup
import io.ehdev.conrad.test.database.repository.PopulateTestUtils
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@Rollback(true)
@Category(IntegrationTestGroup)
@ContextConfiguration(classes = [DatabaseConfiguration.class], loader = SpringApplicationContextLoader.class)
class CommitModelRepositoryTest extends Specification {

    @Autowired
    CommitModelRepository commitModelRepository

    @Autowired
    VcsRepoRepository vcsRepoRepository

    @Autowired
    VersionBumperRepository versionBumperRepository

    VcsRepoModel vcsRepoModel

    def setup() {
        vcsRepoModel = PopulateTestUtils.repo(vcsRepoRepository, versionBumperRepository)
    }

    def 'can find commit by commit id and repo id'() {
        def commit = PopulateTestUtils.createCommit(commitModelRepository, vcsRepoModel, VersionFactory.parse('1.2.3'), null)

        expect:
        commitModelRepository.findByCommitIdAndRepoId(commit.getCommitId(), vcsRepoModel.getUuidAsUUID()) == commit
    }
}
