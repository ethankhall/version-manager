package io.ehdev.conrad.gradle

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class SpringRestDocPluginTest extends Specification {

    def 'can apply to project'() {
        when:
        ProjectBuilder.builder().build().plugins.apply(SpringRestDocPlugin)

        then:
        noExceptionThrown()
    }
}
