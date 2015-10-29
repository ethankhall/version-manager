package io.ehdev.conrad.model.repo

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import javax.validation.constraints.NotNull

open class RepoCreateModel {

    @NotNull
    @JsonView(RepoView.Public::class)
    var name: String

    @JsonView(RepoView.Public::class)
    var url: String? = null

    @NotNull
    @JsonView(RepoView.Public::class)
    var bumper: String

    @JsonCreator
    constructor(@JsonProperty("name") name: String,
                @JsonProperty("url") url: String?,
                @JsonProperty("bumper") bumper: String) {
        this.name = name
        this.url = url
        this.bumper = bumper
    }
}
