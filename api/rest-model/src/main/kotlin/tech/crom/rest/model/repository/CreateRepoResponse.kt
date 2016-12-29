package tech.crom.rest.model.repository

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel

@OutputModel
class CreateRepoResponse(
    @JsonProperty("projectName")
    val projectName: String,

    @JsonProperty("repoName")
    val repoName: String,

    @JsonProperty("url")
    val url: String? = null)
