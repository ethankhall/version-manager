package io.ehdev.conrad.gradle
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

class SpringRestDocPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            apply plugin: 'idea'
            apply plugin: 'org.asciidoctor.gradle.asciidoctor'

            sourceSets {
                apiTest {
                    groovy {
                        compileClasspath += main.output + test.output
                        runtimeClasspath += main.output + test.output
                        srcDir file('src/apiTest/groovy')
                    }
                    resources.srcDir file('src/apiTest/resources')
                }
            }

            ext {
                snippetsDir = file("$buildDir/generated-snippets")
            }

            tasks.create('apiTest', Test) {
                shouldRunAfter(tasks.test)
                testClassesDir = sourceSets.apiTest.output.classesDir
                classpath = sourceSets.apiTest.runtimeClasspath
                outputs.dir snippetsDir
                environment 'snippitDir', snippetsDir.absolutePath
                doFirst {
                    snippetsDir.mkdirs()
                }
            }

            idea {
                module {
                    testSourceDirs += sourceSets.apiTest.allSource.srcDirs + sourceSets.test.allSource.srcDirs
                    scopes.TEST.plus += [ configurations.apiTestRuntime ]

                }
            }

            configurations {
                apiTestCompile.extendsFrom testCompile
                apiTestRuntime.extendsFrom testRuntime
            }

            dependencies {
                apiTestCompile 'org.springframework.restdocs:spring-restdocs-mockmvc:1.0.1.RELEASE'
            }

            asciidoctor {
                attributes('snippets': snippetsDir)
                inputs.dir snippetsDir
                dependsOn apiTest
                sourceDir 'src/apiTest/asciidoc'
            }

        }
    }
}
