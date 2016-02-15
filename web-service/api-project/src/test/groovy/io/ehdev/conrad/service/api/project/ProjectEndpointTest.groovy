package io.ehdev.conrad.service.api.project
import io.ehdev.conrad.database.api.ProjectManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.project.ApiProjectModel
import io.ehdev.conrad.service.api.service.ProjectEndpoint
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class ProjectEndpointTest extends Specification {

    ProjectEndpoint projectEndpoint
    ProjectManagementApi projectManagementApi = Mock(ProjectManagementApi)

    def setup() {
        projectEndpoint = new ProjectEndpoint(projectManagementApi)
    }

    def 'can create project'() {
        when:
        projectEndpoint.createProject(new ApiParameterContainer(null, 'newProject', null), new MockHttpServletRequest())

        then:
        1 * projectManagementApi.createProject('newProject') >> new ApiProjectModel('newProject', [])
    }
}
