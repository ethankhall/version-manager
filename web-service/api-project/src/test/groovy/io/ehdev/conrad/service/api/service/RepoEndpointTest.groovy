package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.model.commit.CommitIdCollection
import io.ehdev.conrad.service.api.service.repo.RepoEndpoint
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification
import tech.crom.business.api.CommitApi
import tech.crom.business.api.PermissionApi
import tech.crom.business.api.RepositoryApi
import tech.crom.business.api.VersionBumperApi
import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.repository.CromRepoDetails
import tech.crom.model.security.authorization.CromPermission
import tech.crom.version.bumper.impl.atomic.AtomicVersionBumper

import java.time.ZonedDateTime

import static io.ehdev.conrad.service.api.service.TestUtils.createTestingRepoModel

class RepoEndpointTest extends Specification {

    RepoEndpoint repoEndpoint
    RepositoryApi repositoryApi
    PermissionApi permissionApi
    VersionBumperApi versionBumperApi
    CommitApi commitApi

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        repositoryApi = Mock(RepositoryApi)
        permissionApi = Mock(PermissionApi)
        versionBumperApi = Mock(VersionBumperApi)
        commitApi = Mock(CommitApi)
        repoEndpoint = new RepoEndpoint(repositoryApi, permissionApi, versionBumperApi, commitApi)
    }

    def 'find version finds nothing'() {
        when:
        def model = new CommitIdCollection(['a', 'b', 'c'])
        def history = repoEndpoint.searchForVersionInHistory(createTestingRepoModel(), model)

        then:
        1 * commitApi.findLatestCommit(_, _) >> null
        history.statusCode == HttpStatus.NOT_FOUND
    }

    def 'find version'() {
        when:
        def model = new CommitIdCollection(['a', 'b', 'c'])
        def history = repoEndpoint.searchForVersionInHistory(createTestingRepoModel(), model)

        then:
        1 * commitApi.findLatestCommit(_, _) >> new PersistedCommit(UUID.randomUUID(), 'commit', '2.3.4', ZonedDateTime.now())

        history.statusCode == HttpStatus.OK
        history.body.commitId == 'commit'
        history.body.version == '2.3.4'
    }

    def 'can get details for repo'() {
        def model = createTestingRepoModel()
        def versionBumper = new CromVersionBumper(UUID.randomUUID(), 'atomic', 'foo', '', new AtomicVersionBumper())

        when:
        def details = repoEndpoint.getRepoDetails(model)

        then:
        details.statusCode == HttpStatus.OK

        repositoryApi.getRepoDetails(_) >> new CromRepoDetails(model.cromRepo, versionBumper, true, null, null)
        permissionApi.getPermissions(_) >> [new PermissionApi.PermissionGroup(model.requestPermission.cromUser, CromPermission.ADMIN)]

    }
}
