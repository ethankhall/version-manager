package tech.crom.model.token

import java.time.ZonedDateTime
import java.util.*

data class RetrievedTokenDetails(val id: UUID, val createdAt: ZonedDateTime, val expiresAt: ZonedDateTime)
