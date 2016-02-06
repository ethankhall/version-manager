package io.ehdev.conrad.model.rest.commit

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class RestCommitIdCollection {

    val commits: List<RestCommitIdModel>

    @JsonCreator
    constructor(@JsonProperty("commits") commits: List<RestCommitIdModel>) {
        this.commits = commits
    }
}
