package io.ehdev.conrad.service.api.project
import groovy.json.JsonBuilder
import io.ehdev.conrad.api.user.config.BaseUserModelResolver
import io.ehdev.conrad.apidoc.ObjectDocumentationSnippet
import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.api.TestDoubleRepoManagementApi
import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.model.rest.RestCommitModel
import io.ehdev.conrad.model.rest.RestRepoCreateModel
import io.ehdev.conrad.service.api.config.ApiQualifiedRepoModelResolver
import io.ehdev.conrad.service.api.service.RepoEndpoint
import io.ehdev.conrad.version.bumper.api.VersionBumperService
import org.junit.Rule
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static io.ehdev.conrad.apidoc.StaticMethodDocumentation.*
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
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

    private final String REF_REST_COMMIT_MODEL = '<<class-documentation-RestCommitModel,RestCommitModel>>'
    def model = new ApiRepoModel("bigFizzyDice", 'smallDice', "http://github.com/foo/bar")

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
            .setCustomArgumentResolvers(new BaseUserModelResolver(), new ApiQualifiedRepoModelResolver(repoManagementApi))
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
            pathParameters(defaultParameters())
        )

        def model = new RestRepoCreateModel("semver", "http://github.com/foo/bar")
        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}", 'bigFizzyDice', 'smallDice')
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(model)))
            .andExpect(status().isCreated());
    }

    def 'get-repo-details'() {
        expect:
        createRepo()
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
            pathParameters(defaultParameters())
        )
        mockMvc.perform(
            get("/api/v1/project/{projectName}/repo/{repoName}/details", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

    def 'get-repo-versions'() {
        expect:
        createRepo()
        document.snippets(
            responseFields(
                fieldWithPath("latest").description("The latest commit.").type(REF_REST_COMMIT_MODEL),
                fieldWithPath("commits").description("List of commits.").type(REF_REST_COMMIT_MODEL + '[]'),
            ),
            pathParameters(defaultParameters()),
            createCommitDocumentationSnippet()
        )

        mockMvc.perform(
            get("/api/v1/project/{projectName}/repo/{repoName}/versions", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    ObjectDocumentationSnippet createCommitDocumentationSnippet() {
        return objectSnippits(
            documentObject(RestCommitModel,
                fieldDocumentation('commitId').description('Commit id for commit').withType(JsonFieldType.STRING),
                fieldDocumentation('version').description('Version string for commit').withType(JsonFieldType.STRING),
                fieldDocumentation('versionParts')
                    .description('Array of integers representing the version parts')
                    .withType('int[]'),
                fieldDocumentation('postfix')
                    .description('Postfix for the commit version. Nullable')
                    .withType(JsonFieldType.STRING),
            )
        )
    }

    void createRepo() {
        repoManagementApi.createRepo(model, 'semver', 'http://github.com/foo/bar')
        repoManagementApi.createCommit(model, new ApiCommitModel('1', '1.0.0'), null)
        repoManagementApi.createCommit(model, new ApiCommitModel('2', '2.0.0-BETA'), new ApiCommitModel('1', '1.0.0'))
    }

    ParameterDescriptor[] defaultParameters() {
        [
            parameterWithName("projectName")
                .description("Name of the project to contain the repository.")
                .attributes(key("constraints").value("Must not be null. Must be at least 5 characters long.")),
            parameterWithName("repoName")
                .description("Name of the repository to create.")
                .attributes(key("constraints").value("Must not be null. Must be at least 5 characters long."))
        ].toArray(new ParameterDescriptor[0])
    }

    String toJson(Object o) {
        return new JsonBuilder(o).toPrettyString()
    }
}
