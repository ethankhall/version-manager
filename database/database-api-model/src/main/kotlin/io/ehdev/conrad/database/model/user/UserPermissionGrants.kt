package io.ehdev.conrad.database.model.user

data class UserPermissionGrants(val projectPermissions: List<ProjectPermissionDetails>, val repoPermissions: List<RepoPermissionDetails>) {
    data class ProjectPermissionDetails(val projectName: String, val permission: ApiUserPermission)
    data class RepoPermissionDetails(val projectName: String, val repoName: String, val permission: ApiUserPermission)
}
