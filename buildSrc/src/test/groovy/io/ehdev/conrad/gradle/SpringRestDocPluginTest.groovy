package io.ehdev.conrad.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Ignore
import spock.lang.Specification

@Ignore
class SpringRestDocPluginTest extends Specification {

    def 'can apply to project'() {
        when:
        ProjectBuilder.builder().build().plugins.apply(SpringRestDocPlugin)

        then:
        noExceptionThrown()
    }
}
