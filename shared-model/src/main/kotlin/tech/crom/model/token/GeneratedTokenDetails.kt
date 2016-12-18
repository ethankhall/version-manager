package tech.crom.model.token

import java.time.ZonedDateTime

data class GeneratedTokenDetails(val id: String, val createdAt: ZonedDateTime, val expiresAt: ZonedDateTime, val value: String)
