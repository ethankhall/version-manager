package tech.crom.database.api

import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser
import java.time.LocalDateTime
import java.util.*

interface TokenManager {

    /**
     * Create a user token for CromUser
     */
    fun generateUserToken(cromUser: CromUser, expirationDate: LocalDateTime): GeneratedToken

    /**
     * Create a repo token from CromRepo
     */
    fun generateRepoToken(cromRepo: CromRepo, expirationDate: LocalDateTime): GeneratedToken

    /**
     * is token valid
     */
    fun getTokenData(uid: UUID, tokenType: TokenManager.TokenType): UderlyingTokenDetails?

    /**
     * Kill a token
     */
    fun invalidateToken(uid: UUID, tokenType: TokenManager.TokenType)

    enum class TokenType {
        USER,
        REPOSITORY
    }

    data class UderlyingTokenDetails(val linkedUid: UUID, val tokenType: TokenType)

    data class GeneratedToken(val uuid: UUID,
                              val createDate: LocalDateTime,
                              val expirationDate: LocalDateTime,
                              val valid: Boolean,
                              val tokenType: TokenType)
}
