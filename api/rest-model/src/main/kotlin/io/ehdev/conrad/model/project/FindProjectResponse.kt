package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription

data class FindProjectResponse(val projectDetails: List<ProjectDetails>, val totalSize: Int) {
    data class ProjectDetails(@JsonPropertyDescription("Name of the project.") @JsonProperty("name") val name: String)
}
