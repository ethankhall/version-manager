package tech.crom.security.authentication

import tech.crom.database.api.TokenManager
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import java.time.ZonedDateTime
import java.util.*


fun createGeneratedToken(type: TokenType): TokenManager.TokenDetails {
    val now = ZonedDateTime.now()
    return TokenManager.TokenDetails(UUID.randomUUID(), now, now.plusDays(2), true, type)
}

fun createUser(): CromUser = CromUser(UUID.randomUUID(), "username", "displayName")
