package io.ehdev.conrad.model.repository

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.DefaultResourceSupport

class CreateRepoResponse(
    @JsonProperty("projectName")
    val projectName: String,

    @JsonProperty("repoName")
    val repoName: String,

    @JsonProperty("url")
    val url: String? = null) : DefaultResourceSupport() {
}
