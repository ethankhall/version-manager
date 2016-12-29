package tech.crom.rest.model.version

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import tech.crom.rest.model.InputModel

@InputModel
@JsonClassDescription("Create a new commit.")
class CreateVersionRequest(
    @JsonProperty("commits")
    @JsonPropertyDescription("List of commit ID's that will be used to find the parent commit.")
    var commits: List<String> = mutableListOf(),

    @JsonProperty("message")
    @JsonPropertyDescription("Message used to describe the commit.")
    var message: String,

    @JsonProperty("commitId")
    @JsonPropertyDescription("ID of the new commit.")
    var commitId: String
)
