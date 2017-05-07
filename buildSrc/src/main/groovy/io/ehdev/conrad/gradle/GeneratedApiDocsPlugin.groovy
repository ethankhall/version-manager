package io.ehdev.conrad.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GeneratedApiDocsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            apply plugin: DevelopmentPlugin

            AddTestSourceSets.addSourceSet(project, 'apiDocsTest')
        }
    }
}
