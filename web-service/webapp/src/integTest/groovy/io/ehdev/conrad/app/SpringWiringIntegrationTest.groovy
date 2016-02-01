package io.ehdev.conrad.app

import io.ehdev.conrad.version.bumper.api.VersionBumperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@ContextConfiguration(classes = [TestWebAppConfiguration.class], loader = SpringApplicationContextLoader.class)
class SpringWiringIntegrationTest extends Specification {

    @Autowired
    VersionBumperService versionService

    def 'wiring is good'() {
        expect:
        versionService != null
    }
}
