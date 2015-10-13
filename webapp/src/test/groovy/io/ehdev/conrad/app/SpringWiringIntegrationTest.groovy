package io.ehdev.conrad.app

import io.ehdev.conrad.app.service.strategy.StrategyService
import io.ehdev.conrad.app.service.version.VersionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [MainApplication.class], loader = SpringApplicationContextLoader.class)
@WebIntegrationTest(["server.port=0", "management.port=0"])
class SpringWiringIntegrationTest extends Specification {

    @Autowired
    VersionService versionService

    @Autowired
    StrategyService strategyService

    def 'wiring is good'() {
        expect:
        versionService != null
        strategyService != null
    }
}
