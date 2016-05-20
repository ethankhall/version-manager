package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.database.model.repo.RequestDetails
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails
import io.ehdev.conrad.database.model.repo.details.ResourceDetails
import io.ehdev.conrad.database.model.repo.details.ResourceId
import io.ehdev.conrad.database.model.user.ApiUserPermission
import io.ehdev.conrad.model.version.CreateVersionRequest
import io.ehdev.conrad.service.api.service.repo.RepoVersionEndpoint
import io.ehdev.conrad.version.bumper.api.VersionBumperService
import io.ehdev.conrad.version.bumper.semver.SemanticCommitVersion
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

import java.time.ZonedDateTime

class RepoVersionEndpointTest extends Specification {

    RepoVersionEndpoint repoEndpoint
    RepoManagementApi repoManagementApi
    VersionBumperService versionBumperService

    def setup() {
        repoManagementApi = Mock(RepoManagementApi)
        versionBumperService = Mock(VersionBumperService)
        repoEndpoint = new RepoVersionEndpoint(repoManagementApi, versionBumperService)
    }

    def 'can get all versions'() {

        when:
        def versions = repoEndpoint.getAllVersions(createTestingRepoModel())

        then:
        1 * repoManagementApi.findAllCommits(_) >> [ new ApiCommitModel('abcd1234', '1.2.3', ZonedDateTime.now())]
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
        1 * repoManagementApi.findAllCommits(_) >> [ ]
        versions.getStatusCode() == HttpStatus.OK
        versions.body.commits.size() == 0
        versions.body.latest == null
    }

    def 'can create version'() {
        def versionIds = ['a', 'b', 'c']
        def lastCommit = new ApiCommitModel('a', '1.2.3', null)

        when:
        def model = new CreateVersionRequest(versionIds, "Some Message", "f")
        def request = new MockHttpServletRequest("POST", ":8080/api/v1/project/pn/repo/rp/version")
        def version = repoEndpoint.createNewVersion(createTestingRepoModel(), model, request)

        then:
        1 * repoManagementApi.findLatestCommit(_ as ResourceDetails, _ as List<ApiCommitModel>) >> Optional.of(lastCommit)
        1 * versionBumperService.findNextVersion(_ as ResourceDetails,
            'f', 'Some Message', _ as ApiCommitModel) >> SemanticCommitVersion.parse("1.4.5")
        version.statusCode == HttpStatus.CREATED
        version.body.commitId == 'f'
        version.body.postfix == null
        version.body.version == '1.4.5'
    }

    RequestDetails createTestingRepoModel() {
        def details = new AuthUserDetails(UUID.randomUUID(), 'user', ApiUserPermission.READ, null)
        return new RequestDetails(details, new ResourceDetails(new ResourceId("projectName", null), new ResourceId("repoName", null)))
    }
}
