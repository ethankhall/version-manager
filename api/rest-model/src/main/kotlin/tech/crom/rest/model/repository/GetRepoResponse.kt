package tech.crom.rest.model.repository

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import tech.crom.rest.model.AdminView
import tech.crom.rest.model.OutputModel
import tech.crom.rest.model.permission.PermissionGrant

@OutputModel
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
