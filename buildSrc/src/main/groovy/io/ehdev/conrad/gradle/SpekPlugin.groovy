package io.ehdev.conrad.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class SpekPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply('org.junit.platform.gradle.plugin')

        project.junitPlatform {
            platformVersion '1.0.0-M3'
        }
    }
}
