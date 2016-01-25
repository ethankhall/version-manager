package io.ehdev.conrad.model.repo

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView

class RepoResponseModel : AbstractRepoCreateModel {

    @JsonView(RepoView.Public::class)
    var id: String;

    @JsonView(RepoView.Private::class)
    var token: String;

    @JsonCreator
    constructor(@JsonProperty("name") name: String,
                @JsonProperty("url") url: String?,
                @JsonProperty("bumper") bumper: String,
                @JsonProperty("id") id: String,
                @JsonProperty("token") token: String) : super(name, url, bumper) {
        this.id = id
        this.token = token
    }
}
