package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.permission.UserApiAuthentication
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails
import io.ehdev.conrad.model.commit.CommitIdCollection
import io.ehdev.conrad.service.api.service.repo.RepoEndpoint
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification

class RepoEndpointTest extends Specification {

    RepoEndpoint repoEndpoint
    RepoManagementApi repoManagementApi
    PermissionManagementApi permissionManagementApi

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        repoManagementApi = Mock(RepoManagementApi)
        permissionManagementApi = Mock(PermissionManagementApi)
        repoEndpoint = new RepoEndpoint(repoManagementApi, permissionManagementApi)
    }

    def 'find version finds nothing'() {
        when:
        def model = new CommitIdCollection(['a', 'b', 'c'])
        def history = repoEndpoint.searchForVersionInHistory(createTestingRepoModel(), model)

        then:
        1 * repoManagementApi.findLatestCommit(_, _) >> Optional.empty()
        history.statusCode == HttpStatus.NOT_FOUND
    }

    def 'find version'() {
        when:
        def model = new CommitIdCollection(['a', 'b', 'c'])
        def history = repoEndpoint.searchForVersionInHistory(createTestingRepoModel(), model)

        then:
        1 * repoManagementApi.findLatestCommit(_, _) >> Optional.of(new ApiCommitModel('commit', '2.3.4', null))

        history.statusCode == HttpStatus.OK
        history.body.commitId == 'commit'
        history.body.version == '2.3.4'
    }

    def 'can get details for repo'() {
        when:
        def details = repoEndpoint.getRepoDetails(createTestingRepoModel())

        then:
        details.statusCode == HttpStatus.OK

        repoManagementApi.getDetails(_) >> Optional.of(new ApiRepoDetailsModel(new DefaultApiRepoModel("projectName", "repoName"), new ApiVersionBumperModel(' ', ' ', ' ')))
        permissionManagementApi.getPermissions(_) >> [new ApiUserPermissionDetails('bob', 'ADMIN')]

    }

    ApiParameterContainer createTestingRepoModel() {
        return new ApiParameterContainer(new UserApiAuthentication(UUID.randomUUID(), '','',''), "projectName", "repoName")
    }
}
