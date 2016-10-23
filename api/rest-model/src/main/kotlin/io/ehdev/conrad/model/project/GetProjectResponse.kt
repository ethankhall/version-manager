package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.annotation.JsonView
import io.ehdev.conrad.model.AdminView
import io.ehdev.conrad.model.permission.PermissionGrant

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
