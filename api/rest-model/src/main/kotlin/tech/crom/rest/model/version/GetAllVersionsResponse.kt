package tech.crom.rest.model.version

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import tech.crom.rest.model.OutputModel
import java.time.ZonedDateTime

@OutputModel
class GetAllVersionsResponse() {
    @JsonPropertyDescription("All of the commits for a repository")
    @JsonProperty("commits")
    val commits: MutableList<CommitModel> = mutableListOf()

    @JsonProperty("latest")
    var latest: CommitModel? = null

    class CommitModel(commitId: String, version: String, createdAt: ZonedDateTime) : BaseVersionResponse(commitId, version, createdAt)

    fun addCommit(commit: CommitModel) {
        commits.add(commit)
        latest = commit
    }
}
