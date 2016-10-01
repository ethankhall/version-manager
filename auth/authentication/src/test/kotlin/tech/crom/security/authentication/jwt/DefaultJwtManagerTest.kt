package tech.crom.security.authentication.jwt

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.springframework.mock.env.MockEnvironment
import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import tech.crom.security.authentication.createGeneratedToken
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DefaultJwtManagerTest: Spek({
    val clock = Clock.fixed(LocalDateTime.of(2016, 8, 1, 19, 51).toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
    val env = MockEnvironment()
    env.setProperty("jwt.signing.key", "simple")

    on("create new tokenDetails") {
        it("should return a user tokenDetails") {
            val tokenManager: TokenManager = mock()
            val jwtManager = DefaultJwtManager(clock, env, tokenManager)

            val cromUser = CromUser(UUID.randomUUID(), "username", "displayName")
            val createdToken = createGeneratedToken(TokenType.USER)
            val underlyingToken = TokenManager.UderlyingTokenDetails(cromUser.userUid, TokenType.USER)

            whenever(tokenManager.generateUserToken(any(), any())).thenReturn(createdToken)
            whenever(tokenManager.getTokenData(any(), any())).thenReturn(underlyingToken)

            val token = jwtManager.createUserToken(cromUser)
            assertNotNull(token)

            val parsedToken = jwtManager.parseToken(token) as JwtTokenAuthentication.UserJwtTokenAuthentication
            assertNotNull(parsedToken)
            assertEquals(parsedToken.userUuid, cromUser.userUid)
            assertEquals(parsedToken.userTokenUid, createdToken.uuid)
        }

        it("should return a repo tokenDetails") {
            val tokenManager: TokenManager = mock()
            val jwtManager = DefaultJwtManager(clock, env, tokenManager)

            val cromRepo = CromRepo(UUID.randomUUID(), 1, UUID.randomUUID(), "repoName", UUID.randomUUID())
            val createdToken = createGeneratedToken(TokenType.REPOSITORY)
            val underlyingToken = TokenManager.UderlyingTokenDetails(cromRepo.repoUid, TokenType.REPOSITORY)

            whenever(tokenManager.generateRepoToken(any(), any())).thenReturn(createdToken)
            whenever(tokenManager.getTokenData(any(), any())).thenReturn(underlyingToken)

            val token = jwtManager.createRepoToken(cromRepo)
            assertNotNull(token)

            val parsedToken = jwtManager.parseToken(token) as JwtTokenAuthentication.RepoJwtTokenAuthentication
            assertNotNull(parsedToken)
            assertEquals(parsedToken.repoUuid, cromRepo.repoUid)
            assertEquals(parsedToken.repoTokenUid, createdToken.uuid)
        }

        it("should return null when a tokenDetails is bad") {
            val tokenManager: TokenManager = mock()
            val jwtManager = DefaultJwtManager(clock, env, tokenManager)

            assertNull(jwtManager.parseToken("tokenDetails.tokenDetails.tokenDetails"))
            assertNull(jwtManager.parseToken("tokenDetails"))
            assertNull(jwtManager.parseToken(""))
            assertNull(jwtManager.parseToken(null))
        }
    }
})
