package io.ehdev.conrad.app.gradle

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import groovy.json.JsonBuilder
import io.ehdev.conrad.model.version.GetVersionResponse
import nebula.test.PluginProjectSpec
import org.junit.Rule
import spock.lang.Unroll

class VersionManagerPluginTest extends PluginProjectSpec {

    @Rule
    public final MockWebServer server = new MockWebServer();

    @Override
    String getPluginName() {
        return 'version-manager'
    }

    def 'extension added'() {
        when:
        project.apply plugin: pluginName

        then:
        null != project.getExtensions().findByName('versionManager')
        null != project.getExtensions().findByType(VersionManagerExtension)
    }

    def 'version resolve happens as expected'() {
        when:
        project.apply plugin: pluginName

        def commitModel = new GetVersionResponse('1', '1.2.5-SNAPSHOT')
        configureProject(project.getExtensions().getByType(VersionManagerExtension), new JsonBuilder(commitModel).toString())

        then:
        project.getVersion().toString() == '1.2.5-SNAPSHOT'
    }

    @Unroll
    def 'when error happens it will return with a default value with status code #statusCode'() {
        when:
        project.apply plugin: pluginName

        configureProject(project.getExtensions().getByType(VersionManagerExtension), "SOME TEXT", statusCode)

        then:
        project.getVersion().toString() == '0.0.1-SNAPSHOT'

        where:
        statusCode << [201, 200]
    }

    public void configureProject(VersionManagerExtension configuration, String body, int statusCode = 200) {
        server.enqueue(new MockResponse().setBody(body).setResponseCode(statusCode))

        configure(configuration)
    }

    private void configure(VersionManagerExtension configuration) {
        configuration.setApplicationUrl('http://localhost:' + server.getPort())
        configuration.setProjectName(UUID.randomUUID().toString())
        configuration.setRepoName(UUID.randomUUID().toString())
    }

}
