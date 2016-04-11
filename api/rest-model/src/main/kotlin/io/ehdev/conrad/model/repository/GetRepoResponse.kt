package io.ehdev.conrad.model.repository

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.DefaultResourceSupport
import io.ehdev.conrad.model.permission.PermissionGrant

class GetRepoResponse(
    @JsonProperty("projectName") val projectName: String,
    @JsonProperty("repoName") val repoName: String,
    @JsonProperty("url") val url: String?): DefaultResourceSupport() {

    @JsonProperty("permissions")
    val permissions: MutableList<PermissionGrant> = mutableListOf()

    fun addPermission(permission: PermissionGrant) {
        permissions.add(permission)
    }
}
