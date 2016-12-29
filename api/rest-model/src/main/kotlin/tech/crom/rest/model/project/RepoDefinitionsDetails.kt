package tech.crom.rest.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription

class RepoDefinitionsDetails(@JsonPropertyDescription("Name of the project.") @JsonProperty("name") val name: String)
