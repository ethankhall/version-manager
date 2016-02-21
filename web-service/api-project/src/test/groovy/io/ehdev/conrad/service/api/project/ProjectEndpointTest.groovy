package io.ehdev.conrad.service.api.project

import io.ehdev.conrad.database.api.ProjectManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.user.ApiUser
import io.ehdev.conrad.service.api.service.ProjectEndpoint
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification

class ProjectEndpointTest extends Specification {

    ProjectEndpoint projectEndpoint
    ProjectManagementApi projectManagementApi = Mock(ProjectManagementApi)

    def setup() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        projectEndpoint = new ProjectEndpoint(projectManagementApi)
    }

    def 'can create project'() {
        def apiUser = new ApiUser(UUID.randomUUID(), 'username', 'name', 'email')
        def container = new ApiParameterContainer(apiUser, 'newProject', null)

        when:
        projectEndpoint.createProject(container, new MockHttpServletRequest())

        then:
        1 * projectManagementApi.createProject(container)
    }
}
