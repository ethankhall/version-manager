package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel
import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel
import org.springframework.http.HttpStatus
import spock.lang.Specification

class RepoDetailsEndpointTest extends Specification {
    RepoDetailsEndpoint endpoint
    RepoManagementApi repoManagementApi

    def setup() {
        repoManagementApi = Mock(RepoManagementApi)
        endpoint = new RepoDetailsEndpoint(repoManagementApi)
    }

    def 'can get details for repo'() {
        when:
        def details = endpoint.getRepoDetails(createTestingRepoModel(), null)

        then:
        details.statusCode == HttpStatus.OK

        repoManagementApi.getDetails(_) >> Optional.of(new ApiRepoDetailsModel(createTestingRepoModel(), new ApiVersionBumperModel(' ', ' ', ' ')))

    }

    ApiRepoModel createTestingRepoModel() {
        return new ApiRepoModel("projectName", "repoName")
    }
}
