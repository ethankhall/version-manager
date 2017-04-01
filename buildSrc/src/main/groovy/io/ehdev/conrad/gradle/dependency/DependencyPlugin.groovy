package io.ehdev.conrad.gradle.dependency

import groovy.json.JsonSlurper
import netflix.nebula.dependency.recommender.DependencyRecommendationsPlugin
import netflix.nebula.dependency.recommender.provider.RecommendationProviderContainer
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

import java.util.function.Consumer

class DependencyPlugin implements Plugin<Project> {

    static final Logger logger = Logging.getLogger(DependencyPlugin)

    @Override
    void apply(Project project) {
        project.plugins.apply(DependencyRecommendationsPlugin)

        Map<String, Map> deps = buildUpMap(project)

        def container = project.extensions.getByType(RecommendationProviderContainer)
        def recommendationProvider = new JsonRecommendationProvider(deps.suggestions as List<Map<String, String>>)
        container.add(recommendationProvider)

        project.configurations.all(new Action<Configuration>() {
            @Override
            void execute(Configuration configuration) {
                configuration.resolutionStrategy.eachDependency(new Action<DependencyResolveDetails>() {
                    @Override
                    void execute(DependencyResolveDetails details) {
                        def version = recommendationProvider.getVersion(details.requested.group, details.requested.name)
                        if (version) {
                            details.useVersion(version)
                        }
                    }
                })
            }
        })

        project.extensions.extraProperties['versions'] = deps.version
        project.extensions.extraProperties['libraries'] = deps.libraries
        project.extensions.extraProperties['hibernate.version'] = deps.version.hibernate
        project.extensions.extraProperties['jackson.version'] = deps.version.jackson
        project.extensions.extraProperties['jooq.version'] = deps.version.jooq

        project.configurations.forEach(new Consumer<Configuration>() {
            @Override
            void accept(Configuration files) {
                deps.exclude.each { Map<String, String> dep ->
                    files.exclude(group: dep.group, module: dep.name)
                }
            }
        })
    }

    private static Map<String, Object> buildUpMap(Project project) {
        def slurper = new JsonSlurper()

        def dependencies = new File(project.rootDir, "gradle/dependencies.json").text
        def deps = slurper.parseText(dependencies) as Map<String, Object>
        def replacedInput = dependencies
        deps['version'].each { String key, String value ->
            replacedInput = replacedInput.replaceAll("@version.${key}@", value)
        }

        if (replacedInput.contains("@")) {
            logger.warn(replacedInput)
            throw new RuntimeException("Message still contains @ aborting.")
        }
        return slurper.parseText(replacedInput) as Map<String, Object>
    }
}
