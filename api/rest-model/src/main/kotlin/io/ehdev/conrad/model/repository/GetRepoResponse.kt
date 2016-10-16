package io.ehdev.conrad.model.repository

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import io.ehdev.conrad.model.AdminView
import io.ehdev.conrad.model.permission.PermissionGrant

class GetRepoResponse(
    @JsonProperty("projectName") val projectName: String,
    @JsonProperty("repoName") val repoName: String,
    @JsonProperty("url") val url: String?) {

    @JsonProperty("permissions")
    @JsonView(AdminView::class)
    val permissions: MutableList<PermissionGrant> = mutableListOf()

    fun addPermission(permission: PermissionGrant) {
        permissions.add(permission)
    }
}
