package io.ehdev.conrad.client.java
import groovy.json.JsonBuilder
import io.ehdev.conrad.app.service.ApiFactory
import io.ehdev.conrad.version.commit.VersionFactory
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.model.version.VersionCommitModel
import org.apache.commons.lang3.RandomStringUtils
import org.apache.http.HttpVersion
import org.apache.http.client.HttpClient
import org.apache.http.entity.BasicHttpEntity
import org.apache.http.message.BasicHttpResponse
import spock.lang.Specification

class VersionManagerClientTest extends Specification {

    def 'can get snapshot version'() {
        when:
        def commit = ApiFactory.VersionModelFactory.create(VersionFactory.parse('1.2.5-SNAPSHOT'))
        def requester = createRequester(commit)

        then:
        requester.findVersion((1..10).collect { it.toString() }).getVersion() == '1.2.5-SNAPSHOT'
    }

    def 'can post to version'() {
        when:
        def commit = new VersionCommitModel("10", "1.2.5", [], "")
        def requester = createRequester(commit)

        then:
        requester.claimVersion((1..10).collect { it.toString() }, "some message", '10').getVersion() == '1.2.5'
    }

    public DefaultVersionManagerClient createRequester(def responseObject) {
        def string = new JsonBuilder(responseObject).toString()

        def response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, null)
        def entity = new BasicHttpEntity()
        entity.setContent(new ByteArrayInputStream(string.bytes))
        response.setEntity(entity)

        def client = Mock(HttpClient)
        client.execute(_) >> response

        return new DefaultVersionManagerClient(client, createConfig())
    }

    private VersionServiceConfiguration createConfig() {
        def configuration = new VersionServiceConfiguration()
        configuration.setProviderBaseUrl('http://example.com')
        configuration.setRepoId(UUID.randomUUID().toString())
        configuration.setToken(RandomStringUtils.randomAlphanumeric(30))
        return configuration
    }
}
