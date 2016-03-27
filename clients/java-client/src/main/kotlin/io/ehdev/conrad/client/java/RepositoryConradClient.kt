package io.ehdev.conrad.client.java

import io.ehdev.conrad.model.commit.CommitIdCollection
import io.ehdev.conrad.model.permission.CreateTokenResponse
import io.ehdev.conrad.model.permission.GetTokensResponse
import io.ehdev.conrad.model.permission.PermissionCreateResponse
import io.ehdev.conrad.model.permission.PermissionGrant
import io.ehdev.conrad.model.project.GetProjectResponse
import io.ehdev.conrad.model.repository.CreateRepoRequest
import io.ehdev.conrad.model.repository.CreateRepoResponse
import io.ehdev.conrad.model.version.*
import retrofit2.Call
import retrofit2.http.*

interface RepositoryConradClient {

    @POST("/api/v1/project/{projectName}/repo/{repoName}")
    fun createRepository(@Header("X-AUTH-TOKEN") authorization: String,
                         @Path("projectName") projectName: String,
                         @Path("repoName") repoName: String,
                         @Body body: CreateRepoRequest): Call<CreateRepoResponse>

    @GET("/api/v1/project/{projectName}/repo/{repoName}")
    fun getRepository(@Header("X-AUTH-TOKEN") authorization: String,
                      @Path("projectName") projectName: String,
                      @Path("repoName") repoName: String): Call<GetProjectResponse>

    @POST("/api/v1/project/{projectName}/repo/{repoName}/search/version")
    fun findVersion(@Header("X-AUTH-TOKEN") authorization: String,
                    @Path("projectName") projectName: String,
                    @Path("repoName") repoName: String,
                    @Body commitIdCollection: CommitIdCollection): Call<VersionSearchResponse>

    @POST("/api/v1/project/{projectName}/repo/{repoName}/permissions")
    fun addPermission(@Header("X-AUTH-TOKEN") authorization: String,
                      @Path("projectName") projectName: String,
                      @Path("repoName") repoName: String,
                      @Body permissionGrant: PermissionGrant): Call<PermissionCreateResponse>

    @DELETE("/api/v1/project/{projectName}/repo/{repoName}/permissions/{username}")
    fun deletePermission(@Header("X-AUTH-TOKEN") authorization: String,
                         @Path("projectName") projectName: String,
                         @Path("repoName") repoName: String,
                         @Path("username") username: String)

    @DELETE("/api/v1/project/{projectName}/repo/{repoName}/token/{tokenId}")
    fun deleteToken(@Header("X-AUTH-TOKEN") authorization: String,
                    @Path("projectName") projectName: String,
                    @Path("repoName") repoName: String,
                    @Path("tokenId") username: String)

    @POST("/api/v1/project/{projectName}/repo/{repoName}/token")
    fun createToken(@Header("X-AUTH-TOKEN") authorization: String,
                    @Path("projectName") projectName: String,
                    @Path("repoName") repoName: String): Call<CreateTokenResponse>

    @GET("/api/v1/project/{projectName}/repo/{repoName}/token")
    fun getTokens(@Header("X-AUTH-TOKEN") authorization: String,
                  @Path("projectName") projectName: String,
                  @Path("repoName") repoName: String): Call<GetTokensResponse>


    @GET("/api/v1/project/{projectName}/repo/{repoName}/versions")
    fun getAllVersions(@Header("X-AUTH-TOKEN") authorization: String,
                       @Path("projectName") projectName: String,
                       @Path("repoName") repoName: String): Call<GetAllVersionsResponse>

    @POST("/api/v1/project/{projectName}/repo/{repoName}/version")
    fun createNewVersion(@Header("X-AUTH-TOKEN") authorization: String,
                         @Path("projectName") projectName: String,
                         @Path("repoName") repoName: String,
                         @Body body: CreateVersionRequest): Call<CreateVersionResponse>

    @POST("/api/v1/project/{projectName}/repo/{repoName}/version/{version}")
    fun findVersion(@Header("X-AUTH-TOKEN") authorization: String,
                    @Path("projectName") projectName: String,
                    @Path("repoName") repoName: String,
                    @Path("version") version: String): Call<GetVersionResponse>
}
