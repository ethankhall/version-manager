package io.ehdev.conrad.service.api.project

import groovy.json.JsonBuilder
import io.ehdev.conrad.api.user.config.BaseUserModelResolver
import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.api.TestDoubleRepoManagementApi
import io.ehdev.conrad.model.rest.RestRepoCreateModel
import io.ehdev.conrad.service.api.service.RepoEndpoint
import io.ehdev.conrad.version.bumper.api.VersionBumperService
import org.junit.Rule
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
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

class RepoEndpointApiTest extends Specification {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation(System.getenv('snippitDir'))

    private MockMvc mockMvc
    private RepoEndpoint repoEndpoint
    private RepoManagementApi repoManagementApi = new TestDoubleRepoManagementApi()
    private VersionBumperService versionBumperService
    private RestDocumentationResultHandler document

    def setup() {
        document = document("{method-name}", preprocessResponse(prettyPrint()));
        repoEndpoint = new RepoEndpoint(repoManagementApi, versionBumperService)

        mockMvc = MockMvcBuilders.standaloneSetup(repoEndpoint)
            .setCustomArgumentResolvers(new BaseUserModelResolver())
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(document)
            .build();
    }

    def 'create-repo'() {
        expect:
        document.snippets(
            responseFields(
                fieldWithPath("repo")
                    .description("A JSON object containing details about project structure."),
                fieldWithPath("repo.projectName")
                    .description("Name of the project that manages this repo."),
                fieldWithPath("repo.repoName")
                    .description("Name of the repo"),
                fieldWithPath("repo.url")
                    .description("URL for checkout/cloning the repository.")),
            pathParameters(
                parameterWithName("projectName")
                    .description("Name of the project to contain the repository.")
                    .attributes(key("constraints").value("Must not be null. Must be at least 5 characters long.")),
                parameterWithName("repoName")
                    .description("Name of the repository to create.")
                    .attributes(key("constraints").value("Must not be null. Must be at least 5 characters long."))
            ))

        def model = new RestRepoCreateModel("semver", "http://github.com/foo/bar")
        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}", 'bigFizzyDice', 'smallDice')
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(model)))
            .andExpect(status().isCreated());
    }

    String toJson(Object o) {
        return new JsonBuilder(o).toPrettyString()
    }
}
