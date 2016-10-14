package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription

data class FindProjectResponse(@JsonProperty("projectDetails") val projectDetails: List<ProjectDetails>,
                               @JsonProperty("totalSize") val totalSize: Int) {
    data class ProjectDetails(@JsonPropertyDescription("Name of the project.") @JsonProperty("name") val name: String)
}
