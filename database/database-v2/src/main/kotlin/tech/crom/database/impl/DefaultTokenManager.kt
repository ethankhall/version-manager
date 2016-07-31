package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.daos.RepositoryTokensDao
import io.ehdev.conrad.db.tables.daos.UserTokensDao
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class DefaultTokenManager @Autowired constructor(
    val clock: Clock,
    val dslContext: DSLContext,
    val userTokensDao: UserTokensDao,
    val repositoryTokensDao: RepositoryTokensDao
) : TokenManager {

    override fun getTokenData(uid: UUID, tokenType: TokenManager.TokenType): TokenManager.UderlyingTokenDetails? {
        if (tokenType == TokenManager.TokenType.REPOSITORY) {
            val repositoryToken = repositoryTokensDao.findById(uid)
            if (isTokenValid(repositoryToken.valid, repositoryToken.expiresAt) == false) { return null }
            return TokenManager.UderlyingTokenDetails(repositoryToken.repoUuid, tokenType)
        } else {
            val userToken = userTokensDao.findById(uid)
            if (isTokenValid(userToken.valid, userToken.expiresAt) == false) { return null }
            return TokenManager.UderlyingTokenDetails(userToken.userUuid, tokenType)
        }
    }

    private fun isTokenValid(valid: Boolean, expirationDate: Instant): Boolean {
        return valid && clock.instant().isBefore(expirationDate)
    }

    override fun generateUserToken(cromUser: CromUser, expirationDate: LocalDateTime): TokenManager.GeneratedToken {
        val userTokens = Tables.USER_TOKENS
        val result = dslContext
            .insertInto(userTokens, userTokens.USER_UUID, userTokens.SECURITY_ID, userTokens.EXPIRES_AT)
            .values(cromUser.userUid, cromUser.securityId, expirationDate.toInstant(ZoneOffset.UTC))
            .returning(userTokens.fields().toList())
            .fetchOne()
            .into(userTokens)

        return TokenManager.GeneratedToken(result.uuid,
            LocalDateTime.from(result.createdAt),
            LocalDateTime.from(result.expiresAt),
            result.valid,
            TokenManager.TokenType.USER)
    }

    override fun generateRepoToken(cromRepo: CromRepo, expirationDate: LocalDateTime): TokenManager.GeneratedToken {
        val repoTokens = Tables.REPOSITORY_TOKENS
        val result = dslContext
            .insertInto(repoTokens, repoTokens.REPO_UUID, repoTokens.SECURITY_ID, repoTokens.EXPIRES_AT)
            .values(cromRepo.repoUid, cromRepo.securityId, expirationDate.toInstant(ZoneOffset.UTC))
            .returning(repoTokens.fields().toList())
            .fetchOne()
            .into(repoTokens)

        return TokenManager.GeneratedToken(result.uuid,
            LocalDateTime.from(result.createdAt),
            LocalDateTime.from(result.expiresAt),
            result.valid,
            TokenManager.TokenType.REPOSITORY)
    }

    override fun invalidateToken(uid: UUID, tokenType: TokenManager.TokenType) {
        if (tokenType == TokenManager.TokenType.REPOSITORY) {
            dslContext
                .update(Tables.REPOSITORY_TOKENS)
                .set(Tables.REPOSITORY_TOKENS.VALID, false)
                .where(Tables.REPOSITORY_TOKENS.UUID.eq(uid))
                .execute()
        } else {
            dslContext
                .update(Tables.USER_TOKENS)
                .set(Tables.USER_TOKENS.VALID, false)
                .where(Tables.USER_TOKENS.UUID.eq(uid))
                .execute()
        }
    }
}
