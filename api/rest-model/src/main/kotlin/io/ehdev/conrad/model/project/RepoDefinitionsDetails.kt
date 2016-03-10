package io.ehdev.conrad.model.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import io.ehdev.conrad.model.ResourceLink
import io.ehdev.conrad.model.ResourceSupport

data class RepoDefinitionsDetails(@JsonPropertyDescription("Name of the project.")
                                  @JsonProperty("name")
                                  val name: String,

                                  @JsonProperty("links")
                                  override val links: List<ResourceLink>) : ResourceSupport {
}
