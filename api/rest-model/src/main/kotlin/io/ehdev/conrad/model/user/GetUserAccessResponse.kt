package io.ehdev.conrad.model.user

data class GetUserAccessResponse(val projectPermissions: List<ProjectPermissionDetails>, val repoPermissions: List<RepoPermissionDetails>) {
    data class ProjectPermissionDetails(val projectName: String, val permission: String)
    data class RepoPermissionDetails(val projectName: String, val repoName: String, val permission: String)
}
