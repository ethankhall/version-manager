package io.ehdev.conrad.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResourceLink(@JsonProperty("rel") val rel: String,
                        @JsonProperty("href") val href: String)
