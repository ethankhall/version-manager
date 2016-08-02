package tech.crom.security.authentication

import tech.crom.database.api.TokenManager
import tech.crom.model.user.CromUser
import java.time.LocalDateTime
import java.util.*


fun createGeneratedToken(type: TokenManager.TokenType): TokenManager.GeneratedToken {
    val now = LocalDateTime.now()
    return TokenManager.GeneratedToken(UUID.randomUUID(), now, now.plusDays(2), true, type)
}

fun createUser(): CromUser = CromUser(UUID.randomUUID(), "username", "displayName")
