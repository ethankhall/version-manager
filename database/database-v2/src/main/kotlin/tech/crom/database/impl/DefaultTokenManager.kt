package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import tech.crom.database.api.TokenManager
import tech.crom.db.Tables
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import tech.crom.toZonedDateTime
import java.time.Clock
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

@Service
open class DefaultTokenManager @Autowired constructor(
    internal val clock: Clock,
    internal val dslContext: DSLContext
) : TokenManager {

    @Cacheable("tokensById")
    override fun getTokenData(id: String, tokenType: TokenType): TokenManager.UderlyingTokenDetails? {
        if (tokenType == TokenType.REPOSITORY) {
            val tokensTable = Tables.REPOSITORY_TOKENS
            val repositoryToken = dslContext
                .selectFrom(tokensTable)
                .where(tokensTable.PUBLIC_REPO_TOKEN.eq(id))
                .fetchOne()?.into(tokensTable)

            if (repositoryToken == null || isTokenValid(repositoryToken.valid, repositoryToken.expiresAt) == false) {
                return null
            }
            return TokenManager.UderlyingTokenDetails(repositoryToken.repoId, repositoryToken.repositoryTokenId, tokenType)
        } else {
            val userTokens = Tables.USER_TOKENS
            val userToken = dslContext
                .selectFrom(userTokens)
                .where(userTokens.PUBLIC_USER_TOKEN.eq(id))
                .fetchOne()?.into(userTokens)
            if (userToken == null || isTokenValid(userToken.valid, userToken.expiresAt) == false) {
                return null
            }
            return TokenManager.UderlyingTokenDetails(userToken.userId, userToken.userTokenId, tokenType)
        }
    }

    internal fun isTokenValid(valid: Boolean, expirationDate: Instant): Boolean {
        return valid && clock.instant().isBefore(expirationDate)
    }

    @Caching(evict = arrayOf(
        CacheEvict("tokensByRepo", allEntries = true),
        CacheEvict("tokensByUser", allEntries = true),
        CacheEvict("tokensById", allEntries = true)
    ))
    override fun generateUserToken(cromUser: CromUser, expirationDate: ZonedDateTime): TokenManager.TokenDetails {
        val userTokens = Tables.USER_TOKENS
        val randomToken = "A" + cromUser.userId.toString() + "-" + UUID.randomUUID().toString()
        val result = dslContext
            .insertInto(userTokens, userTokens.USER_ID, userTokens.CREATED_AT, userTokens.EXPIRES_AT, userTokens.PUBLIC_USER_TOKEN)
            .values(cromUser.userId, clock.instant(), expirationDate.toInstant(), randomToken)
            .returning(userTokens.fields().toList())
            .fetchOne()
            .into(userTokens)

        return TokenManager.TokenDetails(result.userTokenId,
            result.publicUserToken,
            result.createdAt.toZonedDateTime(),
            result.expiresAt.toZonedDateTime(),
            result.valid,
            TokenType.USER)
    }

    @Caching(evict = arrayOf(
        CacheEvict("tokensByRepo", allEntries = true),
        CacheEvict("tokensByUser", allEntries = true),
        CacheEvict("tokensById", allEntries = true)
    ))
    override fun generateRepoToken(cromRepo: CromRepo, expirationDate: ZonedDateTime): TokenManager.TokenDetails {
        val randomToken = "B" + cromRepo.repoId.toString() + "-" + UUID.randomUUID().toString()
        val repoTokens = Tables.REPOSITORY_TOKENS
        val result = dslContext
            .insertInto(repoTokens, repoTokens.REPO_ID, repoTokens.CREATED_AT, repoTokens.EXPIRES_AT, repoTokens.PUBLIC_REPO_TOKEN)
            .values(cromRepo.repoId, clock.instant(), expirationDate.toInstant(), randomToken)
            .returning(repoTokens.fields().toList())
            .fetchOne()
            .into(repoTokens)

        return TokenManager.TokenDetails(result.repositoryTokenId,
            result.publicRepoToken,
            result.createdAt.toZonedDateTime(),
            result.expiresAt.toZonedDateTime(),
            result.valid,
            TokenType.REPOSITORY)
    }

    @Cacheable("tokensByRepo")
    override fun findTokens(cromRepo: CromRepo): List<TokenManager.TokenDetails> {
        val tokens = dslContext
            .selectFrom(Tables.REPOSITORY_TOKENS)
            .where(Tables.REPOSITORY_TOKENS.REPO_ID.eq(cromRepo.repoId))
            .and(Tables.REPOSITORY_TOKENS.VALID.eq(true))
            .and(Tables.REPOSITORY_TOKENS.EXPIRES_AT.greaterOrEqual(Instant.now()))
            .and(Tables.REPOSITORY_TOKENS.CREATED_AT.lessOrEqual(Instant.now()))
            .fetch()
            .into(Tables.REPOSITORY_TOKENS)

        return tokens
            .map {
                TokenManager.TokenDetails(
                    it.repositoryTokenId,
                    it.publicRepoToken,
                    it.createdAt.toZonedDateTime(),
                    it.expiresAt.toZonedDateTime(),
                    it.valid,
                    TokenType.REPOSITORY)
            }
            .toList()
    }

    @Cacheable("tokensByUser")
    override fun findTokens(cromUser: CromUser): List<TokenManager.TokenDetails> {
        val tokens = dslContext
            .selectFrom(Tables.USER_TOKENS)
            .where(Tables.USER_TOKENS.USER_ID.eq(cromUser.userId))
            .and(Tables.USER_TOKENS.VALID.eq(true))
            .and(Tables.USER_TOKENS.EXPIRES_AT.greaterOrEqual(Instant.now()))
            .and(Tables.USER_TOKENS.CREATED_AT.lessOrEqual(Instant.now()))
            .fetch()
            .into(Tables.USER_TOKENS)

        return tokens
            .map {
                TokenManager.TokenDetails(
                    it.userTokenId,
                    it.publicUserToken,
                    it.createdAt.toZonedDateTime(),
                    it.expiresAt.toZonedDateTime(),
                    it.valid,
                    TokenType.USER)
            }
            .toList()
    }

    @Caching(evict = arrayOf(
        CacheEvict("tokensByRepo", allEntries = true),
        CacheEvict("tokensByUser", allEntries = true),
        CacheEvict("tokensById", allEntries = true)
    ))
    override fun invalidateToken(id: String, tokenType: TokenType) {
        if (tokenType == TokenType.REPOSITORY) {
            dslContext
                .update(Tables.REPOSITORY_TOKENS)
                .set(Tables.REPOSITORY_TOKENS.VALID, false)
                .where(Tables.REPOSITORY_TOKENS.PUBLIC_REPO_TOKEN.eq(id))
                .execute()
        } else {
            dslContext
                .update(Tables.USER_TOKENS)
                .set(Tables.USER_TOKENS.VALID, false)
                .where(Tables.USER_TOKENS.PUBLIC_USER_TOKEN.eq(id))
                .execute()
        }
    }
}
