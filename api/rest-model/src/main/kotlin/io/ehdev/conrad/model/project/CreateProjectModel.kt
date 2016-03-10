package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import io.ehdev.conrad.model.ResourceLink
import io.ehdev.conrad.model.ResourceSupport

data class CreateProjectModel(@JsonPropertyDescription("Name of the project created")
                              @JsonProperty("name")
                              val name: String,

                              @JsonProperty("links")
                              override val links: List<ResourceLink>) : ResourceSupport {

}
