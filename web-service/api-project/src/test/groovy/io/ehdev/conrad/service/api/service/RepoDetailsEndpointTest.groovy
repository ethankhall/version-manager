package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel
import org.springframework.http.HttpStatus
import spock.lang.Specification

class RepoDetailsEndpointTest extends Specification {
    RepoDetailsEndpoint endpoint
    RepoManagementApi repoManagementApi

    def setup() {
        repoManagementApi = Mock(RepoManagementApi)
        endpoint = new RepoDetailsEndpoint(repoManagementApi, permissionManagementApi)
    }

    def 'can get details for repo'() {
        when:
        def details = endpoint.getRepoDetails(new ApiParameterContainer(null, createTestingRepoModel()))

        then:
        details.statusCode == HttpStatus.OK

        repoManagementApi.getDetails(_) >> Optional.of(new ApiRepoDetailsModel(createTestingRepoModel(), new ApiVersionBumperModel(' ', ' ', ' ')))

    }

    DefaultApiRepoModel createTestingRepoModel() {
        return new DefaultApiRepoModel("projectName", "repoName")
    }
}
