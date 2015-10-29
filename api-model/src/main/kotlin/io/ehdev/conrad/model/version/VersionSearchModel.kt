package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

open class VersionSearchModel {

    @JsonCreator
    constructor(@JsonProperty("commits") commits: List<String>) {
        this.commits = commits
    }

    @NotNull
    var commits: List<String>;
}
