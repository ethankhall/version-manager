package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonProperty

class CreateVersionRequest(@JsonProperty("commits") var commits: List<String> = mutableListOf(),
                           @JsonProperty("message") var message: String,
                           @JsonProperty("commitId") var commitId: String)
