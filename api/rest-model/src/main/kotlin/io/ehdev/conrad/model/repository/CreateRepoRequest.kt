package io.ehdev.conrad.model.repository

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateRepoRequest(
    @JsonProperty("bumper") val bumperName: String,
    @JsonProperty("scmUrl") val repoUrl: String? = null,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("history") val history: List<CreateHistory>? = null) {

    class CreateHistory(@JsonProperty("version") val version: String,
                        @JsonProperty("commitId") val commitId: String)
}
