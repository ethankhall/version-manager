package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.DefaultResourceSupport
import java.time.ZonedDateTime
import java.util.*

class CreateTokenResponse(
    @JsonProperty("id") val uuid: UUID,
    @JsonProperty("createdAt") val createdAt: ZonedDateTime,
    @JsonProperty("expiresAt") val expiresAt: ZonedDateTime,
    @JsonProperty("authToken") val authToken: String) : DefaultResourceSupport()
