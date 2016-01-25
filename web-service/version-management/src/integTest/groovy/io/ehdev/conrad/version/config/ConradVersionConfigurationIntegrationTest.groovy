package io.ehdev.conrad.version.config

import io.ehdev.conrad.version.bumper.SemanticVersionBumper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ConradVersionConfiguration.class], loader = SpringApplicationContextLoader.class)
class ConradVersionConfigurationIntegrationTest extends Specification {

    @Autowired
    SemanticVersionBumper semVerBumper;

    def 'can load config'() {
        expect:
        semVerBumper != null
    }

}
