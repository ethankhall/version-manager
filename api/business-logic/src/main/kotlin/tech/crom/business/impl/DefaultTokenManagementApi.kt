package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.TokenManagementApi
import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.GeneratedTokenDetails
import tech.crom.model.token.RetrievedTokenDetails
import tech.crom.model.token.TokenType
import tech.crom.security.authentication.jwt.JwtManager
import java.time.ZonedDateTime
import java.util.*

@Service
class DefaultTokenManagementApi @Autowired constructor(
    val tokenManager: TokenManager,
    val jwtManager: JwtManager
): TokenManagementApi {

    override fun invalidateToken(id: UUID, tokenType: TokenType) {
        tokenManager.invalidateToken(id, tokenType)
    }

    override fun createToken(cromRepo: CromRepo, expiresAt: ZonedDateTime): GeneratedTokenDetails {
        val generateRepoToken = tokenManager.generateRepoToken(cromRepo, expiresAt)
        val token = jwtManager.createToken(generateRepoToken)
        return GeneratedTokenDetails(generateRepoToken.uuid,
            generateRepoToken.createDate,
            generateRepoToken.expiresAt,
            token)
    }

    override fun getTokens(cromRepo: CromRepo): List<RetrievedTokenDetails> {
        return tokenManager
            .findTokens(cromRepo.repoUid, TokenType.REPOSITORY)
            .map { RetrievedTokenDetails(it.uuid, it.createDate, it.expiresAt) }
    }
}
