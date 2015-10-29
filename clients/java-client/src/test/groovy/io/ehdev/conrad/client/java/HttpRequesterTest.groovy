package io.ehdev.conrad.client.java

import groovy.json.JsonBuilder
import io.ehdev.conrad.app.service.ApiFactory
import io.ehdev.conrad.backend.version.commit.VersionFactory
import io.ehdev.conrad.model.version.UncommitedVersionModel
import org.apache.commons.lang3.RandomStringUtils
import org.apache.http.client.fluent.Content
import org.apache.http.client.fluent.Executor
import org.apache.http.client.fluent.Response
import spock.lang.Specification

class HttpRequesterTest extends Specification {

    def 'can get snapshot version'() {
        when:
        def commit = ApiFactory.VersionModelFactory.create(VersionFactory.parse('1.2.5-SNAPSHOT'))
        def requester = createRequester(commit)

        then:
        requester.getVersion((1..10).collect { it.toString() }).getVersion() == '1.2.5-SNAPSHOT'
    }

    public HttpRequester createRequester(UncommitedVersionModel commitModel) {
        def string = new JsonBuilder(commitModel).toString()

        def content = Mock(Content)
        content.asString() >> string

        def response = Mock(Response)
        response.returnContent() >> content

        def executor = Mock(Executor)
        executor.execute(_) >> response

        return new HttpRequester(executor, createConfig())
    }

    private VersionServiceConfiguration createConfig() {
        def configuration = new VersionServiceConfiguration()
        configuration.setProviderBaseUrl('http://example.com')
        configuration.setRepoId(UUID.randomUUID().toString())
        configuration.setToken(RandomStringUtils.randomAlphanumeric(30))
        return configuration
    }
}
