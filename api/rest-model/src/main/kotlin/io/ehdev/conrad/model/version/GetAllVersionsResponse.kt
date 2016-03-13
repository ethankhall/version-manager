package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import io.ehdev.conrad.model.DefaultResourceSupport

class GetAllVersionsResponse() : DefaultResourceSupport() {
    @JsonPropertyDescription("All of the commits for a repository")
    @JsonProperty("commits")
    val commits: MutableList<CommitModel> = mutableListOf()

    @JsonProperty("latest")
    var latest: CommitModel? = null

    class CommitModel(commitId: String, version: String) : BaseVersionResponse(commitId, version)

    fun addCommit(commit: CommitModel) {
        commits.add(commit)
        latest = commit
    }
}
