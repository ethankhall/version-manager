package tech.crom.rest.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel

@OutputModel
data class GetWatchesResponse(@JsonProperty("watches") val watches: List<WatchDetails>) {
    data class WatchDetails(@JsonProperty("projectName") val projectName: String, @JsonProperty("repoName") val repoName: String?)
}
