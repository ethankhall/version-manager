package io.ehdev.conrad.model.rest

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class RestRepoCreateModel {
    @JsonProperty("bumper")
    val bumperName: String

    @JsonProperty("scmUrl")
    val repoUrl: String

    @JsonCreator constructor(@JsonProperty("bumper") bumperName: String, @JsonProperty("scmUrl") repoUrl: String) {
        this.bumperName = bumperName
        this.repoUrl = repoUrl
    }
}

