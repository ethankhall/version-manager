package io.ehdev.conrad.database.repository
import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.database.config.DatabaseConfiguration
import io.ehdev.conrad.test.IntegrationTestGroup
import io.ehdev.conrad.test.database.repository.PopulateTestUtils
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@Rollback(true)
@Category(IntegrationTestGroup)
@ContextConfiguration(classes = [DatabaseConfiguration.class], loader = SpringApplicationContextLoader.class)
class VersionBumperRepositoryTest extends Specification {

    @Autowired
    VersionBumperRepository versionBumperRepository

    def 'can find bumpers by name'() {
        when:
        def bumper = PopulateTestUtils.createBumper(versionBumperRepository)

        then:
        versionBumperRepository.findByBumperName(SemanticVersionBumper.getSimpleName()) == bumper
        versionBumperRepository.findOne(bumper.getId()) == bumper
    }
}
