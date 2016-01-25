package io.ehdev.conrad.model.repo

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView

open class RepoCreateModel : AbstractRepoCreateModel {

    @JsonView(RepoView.Private::class)
    var history: List<RepoHistory>? = null

    @JsonCreator
    constructor(@JsonProperty("name") name: String,
                @JsonProperty("url") url: String?,
                @JsonProperty("bumper") bumper: String,
                @JsonProperty("history") history: List<RepoHistory>?) : super(name, url, bumper){
        this.history = history
    }
}
