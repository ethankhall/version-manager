package io.ehdev.conrad.model.user

import com.fasterxml.jackson.annotation.JsonProperty

data class GetWatchesResponse(@JsonProperty("watches") val watches: List<WatchDetails>) {
    data class WatchDetails(@JsonProperty("projectName") val projectName: String, @JsonProperty("repoName") val repoName: String?)
}
