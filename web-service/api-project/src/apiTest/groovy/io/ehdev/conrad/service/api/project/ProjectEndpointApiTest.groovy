package io.ehdev.conrad.service.api.project

import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.api.TestDoubleProjectManagementApi
import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.database.model.user.ApiUser
import io.ehdev.conrad.service.api.config.ApiParameterContainerResolver
import io.ehdev.conrad.service.api.service.ProjectEndpoint
import org.junit.Rule
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import spock.lang.Specification

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.restdocs.snippet.Attributes.key
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProjectEndpointApiTest extends Specification {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation(System.getenv('snippitDir'))

    private MockMvc mockMvc
    private ProjectEndpoint projectEndpoint
    private TestDoubleProjectManagementApi projectManagementApi
    private RestDocumentationResultHandler document

    def setup() {
        document = document("{method-name}", preprocessResponse(prettyPrint()));
        projectManagementApi = new TestDoubleProjectManagementApi()
        projectEndpoint = new ProjectEndpoint(projectManagementApi, Mock(PermissionManagementApi))

        mockMvc = MockMvcBuilders.standaloneSetup(projectEndpoint)
            .setCustomArgumentResolvers(new LocalApiParameterContainerResolver())
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(document)
            .build();
    }

    def 'create-project'() {
        expect:
        document.snippets(
            responseFields(
                fieldWithPath("name")
                    .description("Name of the project just created"),
                fieldWithPath("repos")
                    .description("Repositories related to this project")),
            pathParameters(
                parameterWithName("projectName")
                    .description("Name of the project to create.")
                    .attributes(key("constraints").value("Must not be null. Must be at least 5 characters long."))
            ))
        mockMvc.perform(post("/api/v1/project/{projectName}", 'bigFizzyDice').accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    class LocalApiParameterContainerResolver extends ApiParameterContainerResolver {

        public ApiParameterContainer resolveArgument(MethodParameter parameter,
                                                     ModelAndViewContainer mavContainer,
                                                     NativeWebRequest webRequest,
                                                     WebDataBinderFactory binderFactory) throws Exception {
            def result = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory)

            def user = new ApiUser(UUID.randomUUID(), 'user', 'name', 'email')
            return new ApiParameterContainer(user, result.getProjectName(), result.getRepoName())
        }
    }
}
