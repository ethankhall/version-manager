package io.ehdev.conrad.model.commit

import com.fasterxml.jackson.annotation.JsonProperty

class CommitIdCollection(@JsonProperty("commits") val commits: List<String>) { }
