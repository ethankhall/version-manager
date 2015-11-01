package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

open class VersionSearchModel public @JsonCreator constructor(@JsonProperty("commits") val commits: List<String>)
