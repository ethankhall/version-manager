package tech.crom.database.api

import tech.crom.model.repository.CromRepo
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import java.time.ZonedDateTime

interface TokenManager {

    /**
     * Create a user token for CromUser
     */
    fun generateUserToken(cromUser: CromUser, expirationDate: ZonedDateTime): TokenDetails

    /**
     * Create a repo token from CromRepo
     */
    fun generateRepoToken(cromRepo: CromRepo, expirationDate: ZonedDateTime): TokenDetails

    /**
     * is token valid
     */
    fun getTokenData(id: String, tokenType: TokenType): UderlyingTokenDetails?

    /**
     * Kill a token
     */
    fun invalidateToken(id: String, tokenType: TokenType)

    fun findTokens(cromRepo: CromRepo): List<TokenDetails>

    fun findTokens(cromUser: CromUser): List<TokenDetails>

    data class UderlyingTokenDetails(val linkedId: Long, val privateId: Long, val tokenType: TokenType)

    data class TokenDetails(val id: Long,
                            val publicId: String,
                            val createDate: ZonedDateTime,
                            val expiresAt: ZonedDateTime,
                            val valid: Boolean,
                            val tokenType: TokenType)
}
