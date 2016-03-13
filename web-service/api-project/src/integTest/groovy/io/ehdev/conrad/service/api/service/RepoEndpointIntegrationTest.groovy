package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.UserManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.permission.UserApiAuthentication
import io.ehdev.conrad.db.tables.daos.VersionBumpersDao
import io.ehdev.conrad.model.repository.CreateRepoRequest
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
class RepoEndpointIntegrationTest extends Specification {

    @Autowired
    ProjectEndpoint projectEndpoint

    @Autowired
    RepoEndpoint repoEndpoint

    @Autowired
    VersionBumpersDao versionBumpersDao

    @Autowired
    UserManagementApi userManagementApi

    UserApiAuthentication userApi

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        userApi = userManagementApi.createUser("username", "name", "email")
        projectEndpoint.createProject(new ApiParameterContainer(userApi, "project_name", null), new MockHttpServletRequest())
    }

    def 'full workflow'() {
        when:
        def repo = repoEndpoint.createRepo(createApiContainer(), new CreateRepoRequest("semver", 'url', null))

        then:
        repo.statusCode == HttpStatus.CREATED
    }

    private ApiParameterContainer createApiContainer() {
        new ApiParameterContainer(userApi, "project_name", "repo_name")
    }

}
