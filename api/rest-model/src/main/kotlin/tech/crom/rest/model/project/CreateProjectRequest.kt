package tech.crom.rest.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import tech.crom.rest.model.OutputModel

@OutputModel
class CreateProjectRequest(@JsonPropertyDescription("Name of the project created") @JsonProperty("name") val name: String)
