package io.ehdev.conrad.model.rest

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class RestRepoCreateModel @JsonCreator constructor(
    @JsonProperty("bumperName") val bumperName: String,
    @JsonProperty("repoUrl") val repoUrl: String)
