package tech.crom.model.token

import java.time.ZonedDateTime
import java.util.*

data class GeneratedTokenDetails(val id: UUID, val createdAt: ZonedDateTime, val expiresAt: ZonedDateTime, val value: String)
