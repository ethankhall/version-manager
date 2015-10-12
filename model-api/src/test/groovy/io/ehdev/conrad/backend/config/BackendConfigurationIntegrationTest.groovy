package io.ehdev.conrad.backend.config

import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [BackendConfiguration.class], loader = SpringApplicationContextLoader.class)
class BackendConfigurationIntegrationTest extends Specification {

    @Autowired
    SemanticVersionBumper semVerBumper;

    def 'can load config'() {
        expect:
        semVerBumper != null
    }

}
