package io.ehdev.conrad.app.gradle

import io.ehdev.conrad.app.MainApplication
import io.ehdev.conrad.test.IntegrationTestGroup
import org.junit.experimental.categories.Category
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

@Category(IntegrationTestGroup)
@ContextConfiguration(classes = [MainApplication.class], loader = SpringApplicationContextLoader.class)
@WebIntegrationTest(["server.port=0", "management.port=0"])
class VersionManagerPluginIntegrationTest extends Specification {

    @Ignore
    def 'test'() {
        expect:
        !true
    }
}
