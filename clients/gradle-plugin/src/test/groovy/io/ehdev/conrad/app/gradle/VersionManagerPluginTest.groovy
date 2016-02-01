package io.ehdev.conrad.app.gradle
import groovy.json.JsonBuilder
import io.ehdev.conrad.model.rest.RestCommitModel
import nebula.test.PluginProjectSpec
import org.apache.http.HttpVersion
import org.apache.http.client.HttpClient
import org.apache.http.entity.BasicHttpEntity
import org.apache.http.message.BasicHttpResponse
import spock.lang.Unroll

class VersionManagerPluginTest extends PluginProjectSpec {

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

        def commitModel = new RestCommitModel('1', '1.2.5-SNAPSHOT')
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
        def response = new BasicHttpResponse(HttpVersion.HTTP_1_1, statusCode, null)
        def entity = new BasicHttpEntity()
        entity.setContent(new ByteArrayInputStream(body.bytes))
        response.setEntity(entity)

        def client = Mock(HttpClient)
        1 * client.execute(_) >> response

        configuration.setClient(client)
        configure(configuration)
    }

    private static void configure(VersionManagerExtension configuration) {
        configuration.setProviderBaseUrl('http://example.com')
        configuration.setRepoId(UUID.randomUUID().toString())
    }

}
