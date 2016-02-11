package io.ehdev.conrad.model.rest

import com.fasterxml.jackson.annotation.JsonProperty

class RestRepoDetailsModel {
    @JsonProperty("repo")
    val repo: RestRepoModel

    constructor(repo: RestRepoModel) {
        this.repo = repo
    }
}
