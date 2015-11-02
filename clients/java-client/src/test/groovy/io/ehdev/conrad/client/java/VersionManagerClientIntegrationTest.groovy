package io.ehdev.conrad.client.java
import io.ehdev.conrad.app.MainApplication
import io.ehdev.conrad.backend.version.commit.VersionFactory
import io.ehdev.conrad.client.java.internal.DefaultVersionManagerClient
import io.ehdev.conrad.database.repository.CommitModelRepository
import io.ehdev.conrad.database.repository.VcsRepoRepository
import io.ehdev.conrad.database.repository.VersionBumperRepository
import io.ehdev.conrad.test.IntegrationTestGroup
import io.ehdev.conrad.test.database.repository.PopulateTestUtils
import org.apache.http.impl.client.DefaultHttpClient
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@Rollback
@ActiveProfiles("test")
@Category(IntegrationTestGroup)
@ContextConfiguration(classes = [MainApplication.class], loader = SpringApplicationContextLoader.class)
@WebIntegrationTest(["server.port=0", "management.port=0"])
class VersionManagerClientIntegrationTest extends Specification {

    @Autowired
    EmbeddedWebApplicationContext server;

    @Value('${local.server.port}')
    int port;

    @Autowired
    VcsRepoRepository vcsRepoRepository

    @Autowired
    VersionBumperRepository versionBumperRepository

    @Autowired
    CommitModelRepository commitModelRepository

    def 'can get snapshot version'() {
        given:
        def vcsRepoModel = PopulateTestUtils.repo(vcsRepoRepository, versionBumperRepository)
        def parentCommit = null
        def commits = (0..9).collect {
            def temp = PopulateTestUtils.createCommit(commitModelRepository, vcsRepoModel, VersionFactory.parse('1.2.' + it), parentCommit)
            parentCommit = temp;
            return temp
        }

        def configuration = createVersionServiceConfiguration(vcsRepoModel.getUuid())
        def requester = new DefaultVersionManagerClient(new DefaultHttpClient(), configuration)

        when:
        def version = requester.findVersion(commits.collect { it.commitId })

        then:
        commits.last().version.toVersionString() == '1.2.9'
        version.version == '1.2.10-SNAPSHOT'
    }

    def 'can claim real version'() {
        given:
        def vcsRepoModel = PopulateTestUtils.repo(vcsRepoRepository, versionBumperRepository)
        def parentCommit = null
        def commits = (0..9).collect {
            def temp = PopulateTestUtils.createCommit(commitModelRepository, vcsRepoModel, VersionFactory.parse('1.2.' + it), parentCommit)
            parentCommit = temp;
            return temp
        }

        def configuration = createVersionServiceConfiguration(vcsRepoModel.getUuid(), vcsRepoModel.getToken())
        def requester = new DefaultVersionManagerClient(new DefaultHttpClient(), configuration)

        when:
        def version = requester.claimVersion(commits.collect { it.commitId }, "This is a commit\n[bump major]", 'a' * 10)

        then:
        commits.last().version.toVersionString() == '1.2.9'
        version.version == '2.0.0'
    }

    VersionServiceConfiguration createVersionServiceConfiguration(String id, String token = null) {
        def configuration = new VersionServiceConfiguration()
        configuration.setProviderBaseUrl("http://localhost:${port}")
        configuration.setRepoId(id)
        configuration.setToken(token)
        return configuration
    }
}
