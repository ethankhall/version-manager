package io.ehdev.conrad.model.rest

import com.fasterxml.jackson.annotation.JsonProperty

class RestRepoModel {
    @JsonProperty("projectName")
    val projectName: String

    @JsonProperty("repoName")
    val repoName: String

    @JsonProperty("url")
    val url: String?

    constructor(projectName: String, repoName: String, url: String?) {
        this.projectName = projectName
        this.repoName = repoName
        this.url = url
    }
}
