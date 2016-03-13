package io.ehdev.conrad.client.java

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import groovy.json.JsonBuilder
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.model.version.GetVersionResponse
import okhttp3.OkHttpClient
import org.junit.Rule
import spock.lang.Specification

class VersionManagerClientTest extends Specification {

    @Rule
    public final MockWebServer server = new MockWebServer();

    def 'can get snapshot version'() {
        when:
        def commit = new GetVersionResponse('id', '1.2.5-SNAPSHOT')
        def requester = createRequester(commit)

        then:
        requester.findVersion((1..10).collect { it.toString() }).getVersion() == '1.2.5-SNAPSHOT'
    }

    def 'can post to version'() {
        when:
        def commit = new GetVersionResponse("10", "1.2.5")
        def requester = createRequester(commit)

        then:
        requester.claimVersion((1..10).collect { it.toString() }, "some message", '10').getVersion() == '1.2.5'
    }

    public DefaultVersionManagerClient createRequester(def responseObject) {
        def string = new JsonBuilder(responseObject).toString()
        server.enqueue(new MockResponse().setBody(string).setResponseCode(200))

        return new DefaultVersionManagerClient(new OkHttpClient(), createConfig())
    }

    private VersionServiceConfiguration createConfig() {
        def configuration = new VersionServiceConfiguration()
        configuration.setApplicationUrl('http://localhost:' + server.getPort())
        configuration.setProjectName(UUID.randomUUID().toString())
        configuration.setRepoName(UUID.randomUUID().toString())
        return configuration
    }
}
