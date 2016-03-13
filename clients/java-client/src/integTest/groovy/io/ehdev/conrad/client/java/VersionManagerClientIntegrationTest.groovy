package io.ehdev.conrad.client.java

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import groovy.json.JsonBuilder
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.model.version.GetVersionResponse
import okhttp3.OkHttpClient
import org.junit.Rule
import spock.lang.Specification

class VersionManagerClientIntegrationTest extends Specification {

    @Rule
    public final MockWebServer server = new MockWebServer();

    def 'can get snapshot version'() {
        def jsonPretty = new JsonBuilder(new GetVersionResponse("id", "1.2.3.5-SNAPSHOT")).toPrettyString()

        given:
        server.enqueue(new MockResponse().setBody(jsonPretty).setResponseCode(200).addHeader("Content-Type", "application/json"))
        def requester = new DefaultVersionManagerClient(new OkHttpClient(), createConfig())

        when:
        def version = requester.findVersion(['1.2.3.4', '1.2.3.3', '1.2.3.2', '1.2.3.1', '1.2.3.0'])

        then:
        version.version == '1.2.3.5-SNAPSHOT'

        def request = server.takeRequest()
        request.path =~ "/api/v1/project/(.*?)/repo/(.*?)/search/version"
        request.method == 'POST'
    }

    def 'can claim real version'() {
        def jsonPretty = new JsonBuilder(new GetVersionResponse("id", "2.0.0")).toPrettyString()

        given:
        server.enqueue(new MockResponse().setBody(jsonPretty).setResponseCode(200).addHeader("Content-Type", "application/json"))
        def requester = new DefaultVersionManagerClient(new OkHttpClient(), createConfig())
        when:
        def version = requester.claimVersion((1..10).collect { it as String }, "This is a commit\n[bump major]", 'a' * 10)

        then:
        version.version == '2.0.0'
        def request = server.takeRequest()
        request.path =~ "/api/v1/project/(.*?)/repo/(.*?)/version"
        request.method == 'POST'
    }

    private VersionServiceConfiguration createConfig() {
        def configuration = new VersionServiceConfiguration()
        configuration.setApplicationUrl('http://localhost:' + server.getPort())
        configuration.setProjectName(UUID.randomUUID().toString())
        configuration.setRepoName(UUID.randomUUID().toString())
        return configuration
    }
}
