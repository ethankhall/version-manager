package tech.crom.webapp.app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification
import tech.crom.business.api.VersionBumperApi

import javax.transaction.Transactional

@WithMockUser
@Transactional
@ContextConfiguration
@TestPropertySource("/application-test.yml")
@SpringBootTest(classes = [TestWebAppConfiguration], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringWiringIntegrationTest extends Specification {

    @Autowired
    VersionBumperApi versionBumperApi

    def 'wiring is good'() {
        expect:
        versionBumperApi != null
    }
}
