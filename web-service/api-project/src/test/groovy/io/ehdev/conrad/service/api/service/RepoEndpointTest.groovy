package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.model.rest.commit.RestCommitIdCollection
import io.ehdev.conrad.model.rest.commit.RestCommitIdModel
import org.springframework.http.HttpStatus
import spock.lang.Specification

class RepoEndpointTest extends Specification {

    RepoEndpoint repoEndpoint
    RepoManagementApi repoManagementApi

    def setup() {
        repoManagementApi = Mock(RepoManagementApi)
        repoEndpoint = new RepoEndpoint(repoManagementApi)
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
