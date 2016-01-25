package io.ehdev.conrad.model.user

import java.time.ZonedDateTime
import java.util.*


class ConradGeneratedToken(uuid: UUID,
                           val tokenType: ConradTokenType,
                           val createdAt: ZonedDateTime,
                           val expiresAt: ZonedDateTime) : ConradToken(uuid)
