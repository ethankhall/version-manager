package tech.crom.rest.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel
import java.time.ZonedDateTime

@OutputModel
class CreateTokenResponse(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("createdAt")
    val createdAt: ZonedDateTime,

    @JsonProperty("expiresAt")
    val expiresAt: ZonedDateTime,

    @JsonProperty("authToken")
    val authToken: String)
