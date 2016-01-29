package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

class VersionCreateModel public @JsonCreator constructor(
    @JsonProperty("commits") commits: List<String>,
    @NotNull @JsonProperty("message") val message: String,
    @NotNull @JsonProperty("commitId") val commitId: String
): VersionSearchModel(commits);
