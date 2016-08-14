package tech.crom.business.api

import tech.crom.model.repository.CromRepo
import tech.crom.model.token.GeneratedTokenDetails
import tech.crom.model.token.RetrievedTokenDetails
import tech.crom.model.token.TokenType
import java.time.ZonedDateTime
import java.util.*

interface TokenManagementApi {
    fun invalidateToken(id: UUID, tokenType: TokenType)

    fun createToken(cromRepo: CromRepo, expiresAt: ZonedDateTime): GeneratedTokenDetails

    fun getTokens(cromRepo: CromRepo): List<RetrievedTokenDetails>
}
