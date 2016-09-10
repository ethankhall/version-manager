package io.ehdev.conrad.model.repository

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class CreateRepoRequest(
    @JsonProperty("bumper") val bumperName: String,
    @JsonProperty("scmUrl") val repoUrl: String? = null,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("history") val history: List<CreateHistory>? = null) {

    class CreateHistory(@JsonProperty("version") val version: String,
                        @JsonProperty("commitId") val commitId: String,
                        @JsonProperty("createdAt") val createdAt: ZonedDateTime?)
}
