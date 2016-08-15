package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import io.ehdev.conrad.db.tables.daos.RepositoryTokensDao
import io.ehdev.conrad.db.tables.daos.UserTokensDao
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import tech.crom.toZonedDateTime
import java.time.Clock
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

@Service
class DefaultTokenManager @Autowired constructor(
    val clock: Clock,
    val dslContext: DSLContext,
    val userTokensDao: UserTokensDao,
    val repositoryTokensDao: RepositoryTokensDao
) : TokenManager {

    override fun getTokenData(uid: UUID, tokenType: TokenType): TokenManager.UderlyingTokenDetails? {
        if (tokenType == TokenType.REPOSITORY) {
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

    override fun generateUserToken(cromUser: CromUser, expirationDate: ZonedDateTime): TokenManager.TokenDetails {
        val userTokens = Tables.USER_TOKENS
        val result = dslContext
            .insertInto(userTokens, userTokens.USER_UUID, userTokens.EXPIRES_AT)
            .values(cromUser.userUid, expirationDate.toInstant())
            .returning(userTokens.fields().toList())
            .fetchOne()
            .into(userTokens)

        return TokenManager.TokenDetails(result.uuid,
            result.createdAt.toZonedDateTime(),
            result.expiresAt.toZonedDateTime(),
            result.valid,
            TokenType.USER)
    }

    override fun generateRepoToken(cromRepo: CromRepo, expirationDate: ZonedDateTime): TokenManager.TokenDetails {
        val repoTokens = Tables.REPOSITORY_TOKENS
        val result = dslContext
            .insertInto(repoTokens, repoTokens.REPO_UUID, repoTokens.EXPIRES_AT)
            .values(cromRepo.repoUid, expirationDate.toInstant())
            .returning(repoTokens.fields().toList())
            .fetchOne()
            .into(repoTokens)

        return TokenManager.TokenDetails(result.uuid,
            result.createdAt.toZonedDateTime(),
            result.expiresAt.toZonedDateTime(),
            result.valid,
            TokenType.REPOSITORY)
    }

    override fun findTokens(cromRepo: UUID, tokenType: TokenType): List<TokenManager.TokenDetails> {
        if (tokenType == TokenType.REPOSITORY) {
            val tokens = dslContext
                .select(Tables.REPOSITORY_TOKENS.fields().toList())
                .from(Tables.REPOSITORY_TOKENS)
                .where(Tables.REPOSITORY_TOKENS.REPO_UUID.eq(cromRepo))
                    .and(Tables.REPOSITORY_TOKENS.VALID.eq(true))
                    .and(Tables.REPOSITORY_TOKENS.EXPIRES_AT.greaterOrEqual(Instant.now()))
                    .and(Tables.REPOSITORY_TOKENS.CREATED_AT.lessOrEqual(Instant.now()))
                .fetch()
                .into(Tables.REPOSITORY_TOKENS)

            return tokens
                .map { TokenManager.TokenDetails(
                    it.uuid,
                    it.createdAt.toZonedDateTime(),
                    it.expiresAt.toZonedDateTime(),
                    it.valid,
                    tokenType) }
                .toList()
        } else {
            val tokens = dslContext
                .select(Tables.USER_TOKENS.fields().toList())
                .from(Tables.USER_TOKENS)
                .where(Tables.USER_TOKENS.USER_UUID.eq(cromRepo))
                    .and(Tables.USER_TOKENS.VALID.eq(true))
                    .and(Tables.USER_TOKENS.EXPIRES_AT.greaterOrEqual(Instant.now()))
                    .and(Tables.USER_TOKENS.CREATED_AT.lessOrEqual(Instant.now()))
                .fetch()
                .into(Tables.USER_TOKENS)

            return tokens
                .map { TokenManager.TokenDetails(
                    it.uuid,
                    it.createdAt.toZonedDateTime(),
                    it.expiresAt.toZonedDateTime(),
                    it.valid,
                    tokenType) }
                .toList()
        }
    }

    override fun invalidateToken(uid: UUID, tokenType: TokenType) {
        if (tokenType == TokenType.REPOSITORY) {
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
