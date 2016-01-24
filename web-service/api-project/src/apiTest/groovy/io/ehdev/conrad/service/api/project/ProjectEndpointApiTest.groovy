package io.ehdev.conrad.service.api.project
import io.ehdev.conrad.api.user.config.BaseUserModelResolver
import io.ehdev.conrad.database.api.TestDoubleProjectManagementApi
import org.junit.Rule
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProjectEndpointApiTest extends Specification {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation(System.getenv('snippitDir'))

    private MockMvc mockMvc
    private ProjectEndpoint projectEndpoint
    private TestDoubleProjectManagementApi projectManagementApi

    def setup() {
        projectManagementApi = new TestDoubleProjectManagementApi()
        projectEndpoint = new ProjectEndpoint(projectManagementApi)
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectEndpoint)
            .setCustomArgumentResolvers(new BaseUserModelResolver())
            .apply(documentationConfiguration(this.restDocumentation))
            .build();
    }

    def 'create project'() {
        expect:
        mockMvc.perform(post("/api/v1/project/projectName").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(document("create-project", responseFields(
                fieldWithPath("name").description("Name of the project just created"),
                fieldWithPath("repos").description("Repositories related to this project")
        )));
    }
}
