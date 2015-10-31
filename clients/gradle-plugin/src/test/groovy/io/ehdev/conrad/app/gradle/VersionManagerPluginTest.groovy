package io.ehdev.conrad.app.gradle
import groovy.json.JsonBuilder
import io.ehdev.conrad.app.service.ApiFactory
import io.ehdev.conrad.backend.version.commit.VersionFactory
import io.ehdev.conrad.model.version.UncommitedVersionModel
import nebula.test.PluginProjectSpec
import org.apache.commons.lang3.RandomStringUtils
import org.apache.http.HttpVersion
import org.apache.http.client.HttpClient
import org.apache.http.entity.BasicHttpEntity
import org.apache.http.message.BasicHttpResponse

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

        def commitModel = ApiFactory.VersionModelFactory.create(VersionFactory.parse('1.2.5-SNAPSHOT'))
        configureProject(project.getExtensions().getByType(VersionManagerExtension), commitModel)

        then:
        project.getVersion().toString() == '1.2.5-SNAPSHOT'
    }

    public void configureProject(VersionManagerExtension configuration, UncommitedVersionModel commitModel) {
        def string = new JsonBuilder(commitModel).toString()

        def response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, null)
        def entity = new BasicHttpEntity()
        entity.setContent(new ByteArrayInputStream(string.bytes))
        response.setEntity(entity)

        def client = Mock(HttpClient)
        1 * client.execute(_) >> response

        configuration.setClient(client)
        configure(configuration)
    }

    private static void configure(VersionManagerExtension configuration) {
        configuration.setProviderBaseUrl('http://example.com')
        configuration.setRepoId(UUID.randomUUID().toString())
        configuration.setToken(RandomStringUtils.randomAlphanumeric(30))
    }

}
