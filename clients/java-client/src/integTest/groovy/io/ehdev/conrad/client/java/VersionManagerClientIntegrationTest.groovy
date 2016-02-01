package io.ehdev.conrad.client.java

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import groovy.json.JsonBuilder
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.model.rest.RestCommitModel
import org.apache.http.impl.client.DefaultHttpClient
import org.junit.Rule
import spock.lang.Specification

class VersionManagerClientIntegrationTest extends Specification {

    @Rule
    WireMockRule wireMockRule = new WireMockRule(8009)

    def wireMockStub = new WireMockGroovy(8009)


    def 'can get snapshot version'() {
        given:
        wireMockStub.stub {
            request {
                method "POST"
                url "/api/version/repo-name/search"
            }
            response {
                status 200
                body new JsonBuilder(new RestCommitModel("id", "1.2.3.5-SNAPSHOT")).toPrettyString()
                headers {
                    "Content-Type" "application/json"
                }
            }
        }

        def configuration = createVersionServiceConfiguration('repo-name')
        def requester = new DefaultVersionManagerClient(new DefaultHttpClient(), configuration)

        when:
        def version = requester.findVersion(['1.2.3.4', '1.2.3.3', '1.2.3.2', '1.2.3.1', '1.2.3.0'])

        then:
        version.version == '1.2.3.5-SNAPSHOT'
    }

    def 'can claim real version'() {
        given:
        wireMockStub.stub {
            request {
                method "POST"
                url "/api/version/repo-name"
            }
            response {
                status 200
                body new JsonBuilder(new RestCommitModel("id", "2.0.0")).toPrettyString()
                headers {
                    "Content-Type" "application/json"
                }
            }
        }

        def configuration = createVersionServiceConfiguration('repo-name')
        def requester = new DefaultVersionManagerClient(new DefaultHttpClient(), configuration)

        when:
        def version = requester.claimVersion((1..10).collect { it as String }, "This is a commit\n[bump major]", 'a' * 10)

        then:
        version.version == '2.0.0'
    }

    VersionServiceConfiguration createVersionServiceConfiguration(String id) {
        def configuration = new VersionServiceConfiguration()
        configuration.setProviderBaseUrl("http://localhost:8009")
        configuration.setRepoId(id.toString())
        return configuration
    }
}
