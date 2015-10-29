package io.ehdev.conrad.database.repository
import io.ehdev.conrad.database.config.DatabaseConfiguration
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
class VcsRepoRepositoryTest extends Specification {

    @Autowired
    VcsRepoRepository vcsRepoRepository

    @Autowired
    VersionBumperRepository versionBumperRepository

    def 'can be found'() {
        def vcs = PopulateTestUtils.repo(vcsRepoRepository, versionBumperRepository)

        expect:
        vcsRepoRepository.findByUuid(vcs.getUuidAsUUID()) == vcs
        vcsRepoRepository.findOne(vcs.getId()) == vcs
    }
}
