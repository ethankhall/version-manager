package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.model.rest.commit.RestCommitIdCollection
import io.ehdev.conrad.model.rest.commit.RestCommitIdModel
import io.ehdev.conrad.model.version.VersionCreateModel
import io.ehdev.conrad.version.bumper.api.VersionBumperService
import io.ehdev.conrad.version.commit.CommitVersion
import io.ehdev.conrad.version.commit.VersionFactory
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class RepoEndpointTest extends Specification {

    RepoEndpoint repoEndpoint
    RepoManagementApi repoManagementApi
    VersionBumperService versionBumperService

    def setup() {
        repoManagementApi = Mock(RepoManagementApi)
        versionBumperService = Mock(VersionBumperService)
        repoEndpoint = new RepoEndpoint(repoManagementApi, versionBumperService)
    }

    def 'can get all versions'() {

        when:
        def versions = repoEndpoint.getAllVersions(createTestingRepoModel(), null)

        then:
        1 * repoManagementApi.findAllCommits(_) >> [ new ApiCommitModel('abcd1234', '1.2.3')]
        versions.getStatusCode() == HttpStatus.OK
        versions.body.commits.size() == 1
        versions.body.commits[0].commitId == 'abcd1234'
        versions.body.commits[0].version == '1.2.3'
        versions.body.latest == versions.body.commits[0]
    }

    def 'when there are no versions, it will pass'() {

        when:
        def versions = repoEndpoint.getAllVersions(createTestingRepoModel(), null)

        then:
        1 * repoManagementApi.findAllCommits(_) >> [ ]
        versions.getStatusCode() == HttpStatus.OK
        versions.body.commits.size() == 0
        versions.body.latest == null
    }

    def 'can create version'() {
        def versionIds = ['a', 'b', 'c']
        def lastCommit = new ApiCommitModel('a', '1.2.3')

        when:
        def model = new VersionCreateModel(versionIds, "Some Message", "f")
        def request = new MockHttpServletRequest("POST", ":8080/api/v1/project/pn/repo/rp/version")
        def version = repoEndpoint.createNewVersion(createTestingRepoModel(), model, request, null)

        then:
        1 * repoManagementApi.findLatestCommit(_, _) >> Optional.of(lastCommit)
        1 * versionBumperService.findNextVersion(_ as ApiRepoModel,
            'f', 'Some Message', _ as CommitVersion) >> VersionFactory.parse("1.4.5")
        version.statusCode == HttpStatus.CREATED
        version.body.commitId == 'f'
        version.body.postfix == null
        version.body.version == '1.4.5'
    }

    def 'find version finds nothing'() {
        def versionIds = ['a', 'b', 'c']

        when:
        def model = new RestCommitIdCollection(versionIds.collect { new RestCommitIdModel(it) })
        def history = repoEndpoint.searchForVersionInHistory(createTestingRepoModel(), model, null)

        then:
        1 * repoManagementApi.findLatestCommit(_, _) >> Optional.empty()
        history.statusCode == HttpStatus.NOT_FOUND
    }

    def 'find version'() {
        def versionIds = ['a', 'b', 'c']

        when:
        def model = new RestCommitIdCollection(versionIds.collect { new RestCommitIdModel(it) })
        def history = repoEndpoint.searchForVersionInHistory(createTestingRepoModel(), model, null)

        then:
        1 * repoManagementApi.findLatestCommit(_, _) >> Optional.of(new ApiCommitModel('commit', '2.3.4'))

        history.statusCode == HttpStatus.OK
        history.body.commitId == 'commit'
        history.body.version == '2.3.4'
    }

    ApiRepoModel createTestingRepoModel() {
        return new ApiRepoModel("projectName", "repoName")
    }
}
