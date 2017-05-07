package tech.crom.rest.model.version

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel
import java.time.ZonedDateTime

@OutputModel
class VersionSearchResponse(commitId: String,
                            version: String?,
                            @JsonProperty("state") val state: String,
                            createdAt: ZonedDateTime?) : BaseVersionResponse(commitId, version, createdAt)
