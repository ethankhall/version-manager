package io.ehdev.conrad.test

import groovy.util.logging.Slf4j
import io.ehdev.conrad.client.java.VersionServiceConfiguration
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.model.repo.RepoCreateModel
import io.ehdev.conrad.model.repo.RepoHistory
import org.apache.commons.lang3.RandomStringUtils
import org.apache.http.impl.client.DefaultHttpClient

@Slf4j
class CreateEntries {

    public static final int numberOfEntries = 10000

    public static void main(String[] args) {
        def configuration = new VersionServiceConfiguration()
        configuration.providerBaseUrl = 'http://172.0.1.100:8080'
        def client = new DefaultVersionManagerClient(new DefaultHttpClient(), configuration)
        def random = Random.newInstance()
        (0..numberOfEntries).each {
            def numberOfCommits = random.nextInt(1000)
            def commits = (0..numberOfCommits).collect { new RepoHistory('0.0.' + it, it as String) }
            log.error("Creating {$it}")
            client.createRepository(new RepoCreateModel(RandomStringUtils.randomAlphanumeric(60), null, 'semver', commits))
        }
    }
}
