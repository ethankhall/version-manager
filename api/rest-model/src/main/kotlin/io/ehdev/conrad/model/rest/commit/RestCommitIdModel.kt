package io.ehdev.conrad.model.rest.commit

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class RestCommitIdModel{

    val commitId: String

    @JsonCreator
    constructor(@JsonProperty("commitId") commitId: String) {
        this.commitId = commitId
    }
}
