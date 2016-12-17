package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

class CreateTokenResponse(
    @JsonProperty("id") val id: Long,
    @JsonProperty("createdAt") val createdAt: ZonedDateTime,
    @JsonProperty("expiresAt") val expiresAt: ZonedDateTime,
    @JsonProperty("authToken") val authToken: String)
