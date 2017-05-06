package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.service.api.service.repo.RepoVersionEndpoint
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification
import tech.crom.business.api.CommitApi
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.rest.model.version.CreateVersionRequest
import tech.crom.security.authorization.impl.AuthUtils

import java.time.ZonedDateTime

import static io.ehdev.conrad.service.api.service.TestUtils.createTestingRepoModel

class RepoVersionEndpointTest extends Specification {

    RepoVersionEndpoint repoEndpoint
    CommitApi commitApi

    def setup() {
        commitApi = Mock(CommitApi)
        repoEndpoint = new RepoVersionEndpoint(commitApi)
    }

    def 'can get all versions'() {

        when:
        def versions = repoEndpoint.getAllVersions(createTestingRepoModel())

        then:
        1 * commitApi.findAllCommits(_) >> [PersistedCommit.createNewCommit(AuthUtils.randomLongGenerator(), 'abcd1234', '1.2.3', ZonedDateTime.now())]
        versions.getStatusCode() == HttpStatus.OK
        versions.body.commits.size() == 1
        versions.body.commits[0].commitId == 'abcd1234'
        versions.body.commits[0].version == '1.2.3'
        versions.body.latest == versions.body.commits[0]
    }

    def 'when there are no versions, it will pass'() {

        when:
        def versions = repoEndpoint.getAllVersions(createTestingRepoModel())

        then:
        1 * commitApi.findAllCommits(_) >> []
        versions.getStatusCode() == HttpStatus.OK
        versions.body.commits.size() == 0
        versions.body.latest == null
    }

    def 'can create version'() {
        def versionIds = ['a', 'b', 'c']
        def nextCommit = PersistedCommit.createNewCommit(AuthUtils.randomLongGenerator(), 'b', '1.2.4', ZonedDateTime.now())

        when:
        def model = new CreateVersionRequest(versionIds, "Some Message", "f")
        def request = new MockHttpServletRequest("POST", ":8080/api/v1/project/pn/repo/rp/version")
        def version = repoEndpoint.createNewVersion(createTestingRepoModel(), model, request)

        then:
        1 * commitApi.createCommit(_, _, _) >> nextCommit

        version.statusCode == HttpStatus.CREATED
        version.body.commitId == 'f'
        version.body.version == '1.2.4'
    }

}
