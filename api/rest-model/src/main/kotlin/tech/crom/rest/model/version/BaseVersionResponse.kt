package tech.crom.rest.model.version

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

open class BaseVersionResponse(@JsonProperty("commitId") val commitId: String,
                               @JsonProperty("version") val version: String?,
                               @JsonProperty("createdAt") val createdAt: ZonedDateTime?) {
}
