package tech.crom.model.token

import java.time.ZonedDateTime

data class RetrievedTokenDetails(val id: String, val createdAt: ZonedDateTime, val expiresAt: ZonedDateTime)
