package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.TokenManagementApi
import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.GeneratedTokenDetails
import tech.crom.model.token.RetrievedTokenDetails
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import tech.crom.security.authentication.jwt.JwtManager
import java.time.ZonedDateTime
import java.util.*

@Service
class DefaultTokenManagementApi @Autowired constructor(
    val tokenManager: TokenManager,
    val jwtManager: JwtManager
): TokenManagementApi {

    override fun invalidateToken(id: Long, tokenType: TokenType) {
        tokenManager.invalidateToken(id, tokenType)
    }

    override fun createToken(cromUser: CromUser, expiresAt: ZonedDateTime): GeneratedTokenDetails {
        val generateRepoToken = tokenManager.generateUserToken(cromUser, expiresAt)
        val token = jwtManager.createToken(generateRepoToken)
        return GeneratedTokenDetails(generateRepoToken.id,
            generateRepoToken.createDate,
            generateRepoToken.expiresAt,
            token)
    }

    override fun createToken(cromRepo: CromRepo, expiresAt: ZonedDateTime): GeneratedTokenDetails {
        val generateRepoToken = tokenManager.generateRepoToken(cromRepo, expiresAt)
        val token = jwtManager.createToken(generateRepoToken)
        return GeneratedTokenDetails(generateRepoToken.id,
            generateRepoToken.createDate,
            generateRepoToken.expiresAt,
            token)
    }

    override fun getTokens(cromRepo: CromRepo): List<RetrievedTokenDetails> {
        return tokenManager
            .findTokens(cromRepo.repoId, TokenType.REPOSITORY)
            .map { RetrievedTokenDetails(it.id, it.createDate, it.expiresAt) }
    }

    override fun getTokens(cromUser: CromUser): List<RetrievedTokenDetails> {
        return tokenManager
            .findTokens(cromUser.userId, TokenType.USER)
            .map { RetrievedTokenDetails(it.id, it.createDate, it.expiresAt) }
    }
}
