package tech.crom.rest.model.repository

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import tech.crom.rest.model.InputModel
import java.time.ZonedDateTime

@InputModel
@JsonClassDescription("Describes how a repo will be created.")
data class CreateRepoRequest(
    @JsonProperty("bumper")
    @JsonPropertyDescription("The bumper to be used when creating a repo.")
    val bumperName: String,

    @JsonProperty("scmUrl")
    @JsonPropertyDescription("The URL to get the repo, if provided can help when trying to clone a repo.")
    val repoUrl: String? = null,

    @JsonProperty("description")
    @JsonPropertyDescription("Description of the repo.")
    val description: String? = null,

    @JsonProperty("history")
    @JsonPropertyDescription("Seed the repo with history.")
    val history: List<CreateHistory>? = null) {

    @JsonClassDescription("Model used to describe the history of repo to seed.")
    class CreateHistory(
        @JsonProperty("version")
        @JsonPropertyDescription("Version of the new history.")
        val version: String,

        @JsonProperty("commitId")
        @JsonPropertyDescription("Commit for the element in history.")
        val commitId: String,

        @JsonProperty("createdAt")
        @JsonPropertyDescription("An optional time for when the commit was created.")
        val createdAt: ZonedDateTime?
    )
}
