package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription

class GetProjectResponse(
    @JsonPropertyDescription("Name of the project.")
    @JsonProperty("name")
    val name: String,

    @JsonPropertyDescription("List of repos attached to the project.")
    @JsonProperty("repos")
    val repos: List<RepoDefinitionsDetails>)
