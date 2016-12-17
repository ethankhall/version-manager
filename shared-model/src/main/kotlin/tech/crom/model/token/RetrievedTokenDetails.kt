package tech.crom.model.token

import java.time.ZonedDateTime

data class RetrievedTokenDetails(val id: Long, val createdAt: ZonedDateTime, val expiresAt: ZonedDateTime)
