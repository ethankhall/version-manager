package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.api.UserManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.permission.UserApiAuthentication
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel
import io.ehdev.conrad.database.model.user.ApiUserPermission
import io.ehdev.conrad.service.api.config.TestConradProjectApiConfiguration
import io.ehdev.conrad.service.api.service.model.permissions.PermissionGrant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
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
class ProjectPermissionsEndpointIntegrationTest extends Specification {

    @Autowired
    ProjectPermissionsEndpoint projectPermissionsEndpoint

    @Autowired
    ProjectEndpoint projectEndpoint

    @Autowired
    RepoManagementApi repoManagementApi

    @Autowired
    UserManagementApi userManagementApi

    @Autowired
    PermissionManagementApi permissionManagementApi

    UserApiAuthentication adminUser
    UserApiAuthentication testUser
    ApiParameterContainer container;

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        adminUser = userManagementApi.createUser("username", "name", "email")
        testUser = userManagementApi.createUser("test", "test", "test")

        container = new ApiParameterContainer(adminUser, "project_name", null)
        projectEndpoint.createProject(container, new MockHttpServletRequest())
        repoManagementApi.createRepo(new DefaultApiRepoModel("project_name", 'repo_name'), 'semver')
    }

    def 'can add user to permission'() {
        when:
        projectPermissionsEndpoint.addPermission(container, new PermissionGrant("test", "WRITE"))

        then:
        permissionManagementApi.doesUserHavePermission(testUser, "project_name", 'repo_name', ApiUserPermission.WRITE)
        !permissionManagementApi.doesUserHavePermission(testUser, "project_name", 'repo_name', ApiUserPermission.ADMIN)
    }
}