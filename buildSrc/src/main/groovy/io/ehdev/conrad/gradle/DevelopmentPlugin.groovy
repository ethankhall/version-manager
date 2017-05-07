package io.ehdev.conrad.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.tasks.JacocoReport

class DevelopmentPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            apply plugin: 'java'
            apply plugin: 'groovy'
            apply plugin: 'idea'
            apply plugin: 'jacoco'

            sourceCompatibility = '1.8'

            AddTestSourceSets.addSourceSet(project, 'integTest')

            configurations.all {
                exclude group: 'org.apache.tomcat', module: 'tomcat-jdbc'
            }

            tasks.withType(Test) { task ->
                testLogging {
                    afterSuite { desc, result ->
                        if (!desc.parent) { // will match the outermost suite
                            println "Results: ${ result.resultType } (${ result.testCount } tests, ${ result.successfulTestCount } successes, ${ result.failedTestCount } failures, ${ result.skippedTestCount } skipped)"
                        }
                    }
                }
                jacoco {
                    append = false
                    destinationFile = file("$buildDir/jacoco/${ task.name }-jacocoTest.exec")
                    setClassDumpDir(file("$buildDir/jacoco/${ task.name }-classpathdumps"))
                }
            }
            tasks.check.dependsOn(tasks.withType(JacocoReport))
        }
    }
}
