package io.ehdev.conrad.service.api.project

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ehdev.conrad.apidoc.ObjectDocumentationSnippet
import io.ehdev.conrad.database.api.PermissionManagementApi
import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.api.TestDoubleRepoManagementApi
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.database.model.user.ApiRepoUserPermission
import io.ehdev.conrad.model.commit.CommitIdCollection
import io.ehdev.conrad.model.permission.PermissionGrant
import io.ehdev.conrad.model.repository.CreateRepoRequest
import io.ehdev.conrad.model.version.BaseVersionResponse
import io.ehdev.conrad.model.version.CreateVersionRequest
import io.ehdev.conrad.service.api.config.ApiParameterContainerResolver
import io.ehdev.conrad.service.api.service.repo.RepoEndpoint
import io.ehdev.conrad.service.api.service.repo.RepoPermissionsEndpoint
import io.ehdev.conrad.service.api.service.repo.RepoVersionEndpoint
import io.ehdev.conrad.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.version.bumper.api.VersionBumperService
import org.junit.Rule
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.payload.FieldDescriptor
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.restdocs.snippet.Attributes.key
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RepoEndpointApiTest extends Specification {

    private final String REF_REST_COMMIT_MODEL = '<<class-documentation-RestCommitModel,RestCommitModel>>'
    def model = new DefaultApiRepoModel("bigFizzyDice", 'smallDice', "http://github.com/foo/bar")

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation(System.getenv('snippitDir'))

    private MockMvc mockMvc
    private RepoEndpoint repoEndpoint
    private RepoVersionEndpoint repoVersionEndpoint
    private RepoPermissionsEndpoint repoDetailsEndpoint
    private def bumper = new ApiVersionBumperModel(SemanticVersionBumper.getSimpleName(), ' ', 'semver');
    private RepoManagementApi repoManagementApi = new TestDoubleRepoManagementApi(bumper)
    private VersionBumperService versionBumperService = new TestDoubleVersionBumperService()
    private PermissionManagementApi permissionManagementApi = Mock(PermissionManagementApi)
    private RestDocumentationResultHandler document

    def setup() {
        document = document("{method-name}", preprocessResponse(prettyPrint()));
        repoEndpoint = new RepoEndpoint(repoManagementApi, permissionManagementApi)
        repoVersionEndpoint = new RepoVersionEndpoint(repoManagementApi, versionBumperService)
        repoDetailsEndpoint = new RepoPermissionsEndpoint(permissionManagementApi)

        mockMvc = MockMvcBuilders.standaloneSetup(repoEndpoint, repoVersionEndpoint, repoDetailsEndpoint)
            .setCustomArgumentResolvers(new ApiParameterContainerResolver())
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(document)
            .build();
    }

    def 'create-repo'() {
        expect:
        document.snippets(
            requestFields(
                fieldWithPath("history")
                    .description("Any history that the project has that needs to be migrated."),
                fieldWithPath("bumper")
                    .description("Name of the version bumper to use. Most cases will be semver"),
                fieldWithPath("scmUrl")
                    .description("The URL for the scm.")
            ),
            responseFields(
                fieldWithPath("links")
                    .description("Links to resources related to the object"),
                fieldWithPath("projectName")
                    .description("Name of the project that manages this repo."),
                fieldWithPath("repoName")
                    .description("Name of the repo"),
                fieldWithPath("url")
                    .description("URL for checkout/cloning the repository.")),
            pathParameters(defaultParameters())
        )

        def model = new CreateRepoRequest("semver", "http://github.com/foo/bar", null)
        def str = toJson(model)
        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}", 'bigFizzyDice', 'smallDice')
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(str))
            .andExpect(status().isCreated());
    }

    def 'add-permission-repo'() {
        1 * permissionManagementApi.addPermission(_, _, _, _, _) >> true
        expect:
        document.snippets(
            responseFields(
                fieldWithPath("links")
                    .description("Links to resources related to the object"),
                fieldWithPath("accepted")
                    .description("If the request was accepted or not."),),
            pathParameters(defaultParameters())
        )

        def model = new PermissionGrant("test", "ADMIN")
        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}/permissions", 'bigFizzyDice', 'smallDice')
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(model)))
            .andExpect(status().isCreated());
    }

    def 'get-repo-details'() {
        1 * permissionManagementApi.getPermissionsForProject(_) >> [new ApiRepoUserPermission('bob', 'ADMIN')]

        expect:
        createRepo()
        document.snippets(
            responseFields(
                fieldWithPath("links")
                    .description("Links to resources related to the object"),
                fieldWithPath("projectName")
                    .description("Name of the project that manages this repo."),
                fieldWithPath("repoName")
                    .description("Name of the repo"),
                fieldWithPath("url")
                    .description("URL for checkout/cloning the repository."),
                fieldWithPath("permissions[]")
                    .description("Permissions granted to users for the repository"),
                fieldWithPath("permissions[0].username")
                    .description("Username for the permission."),
                fieldWithPath("permissions[0].permission")
                    .description("The permission level granted to the user.")),

            pathParameters(defaultParameters())
        )
        mockMvc.perform(
            get("/api/v1/project/{projectName}/repo/{repoName}", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

    def 'get-repo-versions'() {
        expect:
        createRepo()
        document.snippets(
            responseFields(
                fieldWithPath("links").description("Links to resources related to the object"),
                fieldWithPath("latest").description("The latest commit.").type(REF_REST_COMMIT_MODEL),
                fieldWithPath("commits").description("List of commits.").type(REF_REST_COMMIT_MODEL + '[]'),
            ),
            pathParameters(defaultParameters()),
            createCommitDocumentationSnippet()
        )

        mockMvc.perform(
            get("/api/v1/project/{projectName}/repo/{repoName}/versions", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    def 'post-repo-version'() {
        expect:
        createRepo()
        document.snippets(
            responseFields(defaultVersionResponse()),
            requestFields(
                fieldWithPath('commitId').description('The commitId fo the next version').type(JsonFieldType.STRING),
                fieldWithPath('message').description('Message to be used to calculate the next version.').type(JsonFieldType.STRING),
                fieldWithPath('commits')
                    .description('Array of commitId\'s in the history. Best practice is to include about 50 commits.')
                    .type(JsonFieldType.ARRAY),
            ),
            pathParameters(defaultParameters()),
        )

        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}/version", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(new CreateVersionRequest(['1', '2'], '[bump major version]', '3')))
        )
            .andExpect(status().isCreated());
    }

    def 'post-repo-search'() {
        expect:
        createRepo()
        document.snippets(
            responseFields(defaultVersionResponse()),
            requestFields(
                fieldWithPath("commits[]").description("Array of commitId's to search"),
            ),
            pathParameters(defaultParameters()),
        )

        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}/search/version", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(new CommitIdCollection(['1', '2'])))
        ).andExpect(status().isOk());
    }

    def 'post-repo-search-not-found'() {
        expect:
        createRepo()
        document.snippets(
            requestFields(
                fieldWithPath("commits[]").description("Array of commitId's to search"),
            ),
            pathParameters(defaultParameters()),
        )

        mockMvc.perform(
            post("/api/v1/project/{projectName}/repo/{repoName}/search/version", 'bigFizzyDice', 'smallDice')
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(new CommitIdCollection(['10'])))
        ).andExpect(status().isNotFound());
    }

    ObjectDocumentationSnippet createCommitDocumentationSnippet() {
        return objectSnippits(
            documentObject(new BaseVersionResponse("abc123", "2.4.5-BETA5"),
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
        repoManagementApi.createRepo(model, 'semver')
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

    FieldDescriptor[] defaultVersionResponse() {
        return [
            fieldWithPath('links').description('Links to resources related to the object'),
            fieldWithPath('commitId').description('Commit id for commit').type(JsonFieldType.STRING),
            fieldWithPath('version').description('Version string for commit').type(JsonFieldType.STRING),
            fieldWithPath('versionParts')
                .description('Array of integers representing the version parts')
                .type('int[]'),
            fieldWithPath('postfix')
                .description('Postfix for the commit version. Nullable')
                .type(JsonFieldType.STRING)
        ] as FieldDescriptor[]
    }

    String toJson(Object o) {
        def mapper = new ObjectMapper().registerModule(new KotlinModule())
        def sw = new StringWriter()
        mapper.writeValue(sw, o)

        return sw.toString()
    }
}
