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

@Service
class DefaultTokenManagementApi @Autowired constructor(
    val tokenManager: TokenManager,
    val jwtManager: JwtManager
) : TokenManagementApi {

    override fun invalidateToken(id: String, tokenType: TokenType) {
        tokenManager.invalidateToken(id, tokenType)
    }

    override fun createToken(cromUser: CromUser, expiresAt: ZonedDateTime): GeneratedTokenDetails {
        val generateRepoToken = tokenManager.generateUserToken(cromUser, expiresAt)
        val token = jwtManager.createToken(generateRepoToken)
        return GeneratedTokenDetails(generateRepoToken.publicId,
            generateRepoToken.createDate,
            generateRepoToken.expiresAt,
            token)
    }

    override fun createToken(cromRepo: CromRepo, expiresAt: ZonedDateTime): GeneratedTokenDetails {
        val generateRepoToken = tokenManager.generateRepoToken(cromRepo, expiresAt)
        val token = jwtManager.createToken(generateRepoToken)
        return GeneratedTokenDetails(generateRepoToken.publicId,
            generateRepoToken.createDate,
            generateRepoToken.expiresAt,
            token)
    }

    override fun getTokens(cromRepo: CromRepo): List<RetrievedTokenDetails> {
        return tokenManager
            .findTokens(cromRepo)
            .map { RetrievedTokenDetails(it.publicId, it.createDate, it.expiresAt) }
    }

    override fun getTokens(cromUser: CromUser): List<RetrievedTokenDetails> {
        return tokenManager
            .findTokens(cromUser)
            .map { RetrievedTokenDetails(it.publicId, it.createDate, it.expiresAt) }
    }
}
