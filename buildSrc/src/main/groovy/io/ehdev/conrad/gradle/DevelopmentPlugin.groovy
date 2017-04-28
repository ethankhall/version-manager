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

            sourceSets {
                integTest {
                    java {
                        compileClasspath += main.output + test.output
                        runtimeClasspath += main.output + test.output
                        srcDir file('src/integTest/java')
                    }
                    resources.srcDir file('src/integTest/resources')
                }
            }

            configurations {
                integTestCompile.extendsFrom testCompile
                integTestRuntime.extendsFrom testRuntime
                all {
                    exclude group: 'org.apache.tomcat', module: 'tomcat-jdbc'
                }
            }

            def integTest = tasks.create('integTest', Test) {
                shouldRunAfter(tasks.test)
                testClassesDir = sourceSets.integTest.output.classesDir
                classpath = sourceSets.integTest.runtimeClasspath
                reports.html.destination = file("$buildDir/reports/integration-test")
            }

            tasks.withType(Test) { task ->
                testLogging {
                    afterSuite { desc, result ->
                        if (!desc.parent) { // will match the outermost suite
                            println "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                        }
                    }
                }
                jacoco {
                    append = false
                    destinationFile = file("$buildDir/jacoco/${task.name}-jacocoTest.exec")
                    setClassDumpDir(file("$buildDir/jacoco/${task.name}-classpathdumps"))
                }
            }

            tasks.check.dependsOn(integTest)
            tasks.check.dependsOn(tasks.withType(JacocoReport))

            idea {
                module {
                    testSourceDirs += sourceSets.integTest.groovy.srcDirs
                    testSourceDirs += sourceSets.integTest.java.srcDirs
                    testSourceDirs += sourceSets.integTest.resources.srcDirs
                    scopes.TEST.plus.add(configurations.integTestCompile)
                    scopes.TEST.plus.add(configurations.integTestRuntime)
                }
            }
        }

    }
}
