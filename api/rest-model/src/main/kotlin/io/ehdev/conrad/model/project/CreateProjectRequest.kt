package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import io.ehdev.conrad.model.DefaultResourceSupport

class CreateProjectRequest(
    @JsonPropertyDescription("Name of the project created") @JsonProperty("name") val name: String) : DefaultResourceSupport() {

}
