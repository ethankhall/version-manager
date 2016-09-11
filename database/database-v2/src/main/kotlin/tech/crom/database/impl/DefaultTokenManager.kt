package tech.crom.database.impl

import io.ehdev.conrad.db.Tables
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
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
open class DefaultTokenManager @Autowired constructor(
    val clock: Clock,
    val dslContext: DSLContext
) : TokenManager {

    @Cacheable("tokensById")
    override fun getTokenData(tokenUid: UUID, tokenType: TokenType): TokenManager.UderlyingTokenDetails? {
        if (tokenType == TokenType.REPOSITORY) {
            val tokensTable = Tables.REPOSITORY_TOKENS
            val repositoryToken = dslContext
                .select(tokensTable.fields().toList())
                .from(tokensTable)
                .where(tokensTable.UUID.eq(tokenUid))
                .fetchOne()?.into(tokensTable)

            if (repositoryToken == null || isTokenValid(repositoryToken.valid, repositoryToken.expiresAt) == false) { return null }
            return TokenManager.UderlyingTokenDetails(repositoryToken.repoUuid, tokenType)
        } else {
            val userTokens = Tables.USER_TOKENS
            val userToken = dslContext
                .select(userTokens.fields().toList())
                .from(userTokens)
                .where(userTokens.UUID.eq(tokenUid))
                .fetchOne()?.into(userTokens)
            if (userToken == null || isTokenValid(userToken.valid, userToken.expiresAt) == false) { return null }
            return TokenManager.UderlyingTokenDetails(userToken.userUuid, tokenType)
        }
    }

    private fun isTokenValid(valid: Boolean, expirationDate: Instant): Boolean {
        return valid && clock.instant().isBefore(expirationDate)
    }

    @Caching(evict = arrayOf(
        CacheEvict("tokensByResource", allEntries = true),
        CacheEvict("tokensById", allEntries = true)
    ))
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

    @Caching(evict = arrayOf(
        CacheEvict("tokensByResource", allEntries = true),
        CacheEvict("tokensById", allEntries = true)
    ))
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

    @Cacheable("tokensByResource")
    override fun findTokens(resourceUid: UUID, tokenType: TokenType): List<TokenManager.TokenDetails> {
        if (tokenType == TokenType.REPOSITORY) {
            val tokens = dslContext
                .select(Tables.REPOSITORY_TOKENS.fields().toList())
                .from(Tables.REPOSITORY_TOKENS)
                .where(Tables.REPOSITORY_TOKENS.REPO_UUID.eq(resourceUid))
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
                .where(Tables.USER_TOKENS.USER_UUID.eq(resourceUid))
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

    @Caching(evict = arrayOf(
        CacheEvict("tokensByResource", allEntries = true),
        CacheEvict("tokensById", allEntries = true)
    ))
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
