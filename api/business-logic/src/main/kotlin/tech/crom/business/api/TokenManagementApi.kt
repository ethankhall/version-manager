package tech.crom.business.api

import tech.crom.model.repository.CromRepo
import tech.crom.model.token.GeneratedTokenDetails
import tech.crom.model.token.RetrievedTokenDetails
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import java.time.ZonedDateTime

interface TokenManagementApi {
    fun invalidateToken(id: Long, tokenType: TokenType)

    fun createToken(cromRepo: CromRepo, expiresAt: ZonedDateTime): GeneratedTokenDetails

    fun createToken(cromUser: CromUser, expiresAt: ZonedDateTime): GeneratedTokenDetails

    fun getTokens(cromRepo: CromRepo): List<RetrievedTokenDetails>

    fun getTokens(cromUser: CromUser): List<RetrievedTokenDetails>
}
