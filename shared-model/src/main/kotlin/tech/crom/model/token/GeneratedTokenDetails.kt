package tech.crom.model.token

import java.time.ZonedDateTime

data class GeneratedTokenDetails(val id: Long, val createdAt: ZonedDateTime, val expiresAt: ZonedDateTime, val value: String)
