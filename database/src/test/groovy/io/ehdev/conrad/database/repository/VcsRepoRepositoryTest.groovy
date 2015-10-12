package io.ehdev.conrad.database.repository
import io.ehdev.conrad.database.config.DatabaseConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@Rollback(true)
@ContextConfiguration(classes = [DatabaseConfiguration.class], loader = SpringApplicationContextLoader.class)
class VcsRepoRepositoryTest extends Specification {

    @Autowired
    VcsRepoRepository vcsRepoRepository

    @Autowired
    VersionBumperRepository versionBumperRepository

    def 'can be found'() {
        def vcs = TestUtils.repo(vcsRepoRepository, versionBumperRepository)

        expect:
        vcsRepoRepository.findByUuid(vcs.getUuidAsUUID()) == vcs
        vcsRepoRepository.findOne(vcs.getId()) == vcs
    }
}
