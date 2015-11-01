package io.ehdev.conrad.model.repo

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class RepoHistory public @JsonCreator constructor(@JsonProperty("version") val version: String,
                                                       @JsonProperty("commitId")val commitId: String)

