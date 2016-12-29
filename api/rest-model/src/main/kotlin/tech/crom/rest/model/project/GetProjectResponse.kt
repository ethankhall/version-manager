package tech.crom.rest.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.annotation.JsonView
import tech.crom.rest.model.AdminView
import tech.crom.rest.model.OutputModel
import tech.crom.rest.model.permission.PermissionGrant

@OutputModel
class GetProjectResponse(
    @JsonPropertyDescription("Name of the project.")
    @JsonProperty("name")
    val name: String,

    @JsonPropertyDescription("List of repos attached to the project.")
    @JsonProperty("repos")
    val repos: List<RepoDefinitionsDetails>,

    @JsonProperty("permissions")
    @JsonView(AdminView::class)
    val permissions: MutableList<PermissionGrant>)
