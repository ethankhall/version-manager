package io.ehdev.conrad.service.api.project
import io.ehdev.conrad.database.api.ProjectManagementApi
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
        projectEndpoint.createProject('newProject', new MockHttpServletRequest(), null)

        then:
        1 * projectManagementApi.createProject('newProject')
    }
}
