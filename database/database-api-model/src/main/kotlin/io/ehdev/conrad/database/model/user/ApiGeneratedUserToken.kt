package io.ehdev.conrad.database.model.user

import java.time.ZonedDateTime
import java.util.*

class ApiGeneratedUserToken(override val uuid: UUID,
                            val tokenType: ApiTokenType,
                            val createdAt: ZonedDateTime,
                            val expiresAt: ZonedDateTime
) : ApiToken
