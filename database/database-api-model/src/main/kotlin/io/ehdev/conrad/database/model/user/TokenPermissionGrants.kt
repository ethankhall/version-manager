package io.ehdev.conrad.database.model.user

data class TokenPermissionGrants(val userPermission: UserPermissionDetails?,
                                 val projectPermissions: List<ProjectPermissionDetails>,
                                 val repoPermissions: List<RepoPermissionDetails>) {
    data class UserPermissionDetails(val userName: String)
    data class ProjectPermissionDetails(val projectName: String, val permission: ApiUserPermission)
    data class RepoPermissionDetails(val projectName: String, val repoName: String, val permission: ApiUserPermission)
}
