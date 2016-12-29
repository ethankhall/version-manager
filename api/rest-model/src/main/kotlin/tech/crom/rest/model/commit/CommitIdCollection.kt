package tech.crom.rest.model.commit

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import tech.crom.rest.model.InputModel

@InputModel
@JsonClassDescription("Used when giving a range of commits to operate on.")
class CommitIdCollection(
    @JsonProperty("commits")
    @JsonPropertyDescription("List of commits to search over.")
    val commits: List<String>
)
