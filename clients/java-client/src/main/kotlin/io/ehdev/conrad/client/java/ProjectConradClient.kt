package io.ehdev.conrad.client.java

import io.ehdev.conrad.model.permission.CreateTokenResponse
import io.ehdev.conrad.model.permission.GetTokensResponse
import io.ehdev.conrad.model.permission.PermissionCreateResponse
import io.ehdev.conrad.model.permission.PermissionGrant
import io.ehdev.conrad.model.project.CreateProjectRequest
import io.ehdev.conrad.model.project.GetProjectResponse
import retrofit2.Call
import retrofit2.http.*

interface ProjectConradClient {
    @POST("/api/v1/project/{projectName}")
    fun createProject(@Header("X-AUTH-TOKEN") authorization: String, @Path("projectName") projectName: String): Call<CreateProjectRequest>

    @GET("/api/v1/project/{projectName}")
    fun getProject(@Header("X-AUTH-TOKEN") authorization: String, @Path("projectName") projectName: String): Call<GetProjectResponse>

    @POST("/api/v1/project/{projectName}/permissions")
    fun addPermission(@Header("X-AUTH-TOKEN") authorization: String,
                      @Path("projectName") projectName: String,
                      @Body permissionGrant: PermissionGrant): Call<PermissionCreateResponse>

    @DELETE("/api/v1/project/{projectName}/permissions/{username}")
    fun deletePermission(@Header("X-AUTH-TOKEN") authorization: String,
                         @Path("projectName") projectName: String,
                         @Path("username") username: String)

    @DELETE("/api/v1/project/{projectName}/token/{tokenId}")
    fun deleteToken(@Header("X-AUTH-TOKEN") authorization: String,
                    @Path("projectName") projectName: String,
                    @Path("tokenId") username: String)

    @POST("/api/v1/project/{projectName}/token")
    fun createToken(@Header("X-AUTH-TOKEN") authorization: String,
                    @Path("projectName") projectName: String): Call<CreateTokenResponse>

    @GET("/api/v1/project/{projectName}/token")
    fun getTokens(@Header("X-AUTH-TOKEN") authorization: String,
                  @Path("projectName") projectName: String): Call<GetTokensResponse>

}
