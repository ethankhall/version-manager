package io.ehdev.conrad.test

import groovy.util.logging.Slf4j
import io.ehdev.conrad.client.java.VersionServiceConfiguration
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.model.repository.CreateRepoRequest
import okhttp3.OkHttpClient

@Slf4j
class CreateEntries {

    public static final int numberOfEntries = 10000

    public static void main(String[] args) {
        def configuration = new VersionServiceConfiguration()
        configuration.applicationUrl = 'http://172.0.1.100:8080'
        def client = new DefaultVersionManagerClient(new OkHttpClient(), configuration)
        def random = Random.newInstance()
        (0..numberOfEntries).each {
            def numberOfCommits = random.nextInt(1000)
            def commits = (0..numberOfCommits).collect { new CreateRepoRequest.CreateHistory('0.0.' + it, it as String) }
            log.error("Creating {$it}")
            client.createRepository(new CreateRepoRequest('semver', null, commits))
        }
    }
}
