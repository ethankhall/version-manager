package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.api.UserManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel
import io.ehdev.conrad.database.model.user.ApiUser
import io.ehdev.conrad.db.tables.daos.VersionBumpersDao
import io.ehdev.conrad.model.version.VersionCreateModel
import io.ehdev.conrad.service.api.config.TestConradProjectApiConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification

import javax.transaction.Transactional

@Rollback
@Transactional
@TestPropertySource(properties = ["api.verification = false"])
@ContextConfiguration(classes = [TestConradProjectApiConfiguration], loader = SpringApplicationContextLoader)
class RepoVersionEndpointIntegrationTest extends Specification {

    @Autowired
    RepoVersionEndpoint repoVersionEndpoint

    @Autowired
    ProjectEndpoint projectEndpoint

    @Autowired
    RepoManagementApi repoManagementApi

    @Autowired
    VersionBumpersDao versionBumpersDao

    @Autowired
    UserManagementApi userManagementApi

    ApiUser userApi


    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        userApi = userManagementApi.createUser("username", "name", "email")
        projectEndpoint.createProject(new ApiParameterContainer(userApi, "project_name", null), new MockHttpServletRequest())
    }

    def 'can create a new version for a new repo'() {
        def createModel = new VersionCreateModel([], "message", "1")
        def request = new MockHttpServletRequest()
        request.setRequestURI("http://localhost:8080/")

        when:
        repoManagementApi.createRepo(new DefaultApiRepoModel('project_name', 'repo_name'), 'semver')
        def version = repoVersionEndpoint.createNewVersion(createApiContainer(), createModel, request)

        then:
        version.statusCode == HttpStatus.CREATED
        version.body.version == '0.0.1'
    }

    private ApiParameterContainer createApiContainer() {
        new ApiParameterContainer(userApi, "project_name", "repo_name")
    }
}
