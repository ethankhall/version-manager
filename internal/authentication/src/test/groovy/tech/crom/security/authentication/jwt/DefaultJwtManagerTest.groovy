package tech.crom.security.authentication.jwt

import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification
import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

import static tech.crom.security.authentication.SharedUtilities.createGeneratedToken
import static tech.crom.security.authentication.SharedUtilities.randomNumber

class DefaultJwtManagerTest extends Specification {

    def clock = Clock.fixed(LocalDateTime.of(2016, 8, 1, 19, 51).toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
    def env = new MockEnvironment().withProperty("jwt.signing.key", "simple")

    def 'should return a user tokenDetails'() {
        when:
        def tokenManager = Mock(TokenManager)
        def jwtManager = new DefaultJwtManager(clock, env, tokenManager)

        def cromUser = new CromUser(randomNumber(), "username", "displayName")
        def createdToken = createGeneratedToken(TokenType.USER)
        def underlyingToken = new TokenManager.UderlyingTokenDetails(cromUser.userId, createdToken.id, TokenType.USER)

        tokenManager.generateUserToken(_, _) >> createdToken
        tokenManager.getTokenData(_, _) >> underlyingToken

        then:
        def token = jwtManager.createUserToken(cromUser)
        assert token

        def authentication = jwtManager.parseToken(token) as JwtTokenAuthentication.UserJwtTokenAuthentication
        assert authentication
        authentication.userId == cromUser.userId
        authentication.userTokenId == createdToken.id
    }

    def 'should return null when a tokenDetails is bad'() {
        when:
        def tokenManager = Mock(TokenManager)
        def jwtManager = new DefaultJwtManager(clock, env, tokenManager)

        then:
        !jwtManager.parseToken("tokenDetails.tokenDetails.tokenDetails")
        !jwtManager.parseToken("tokenDetails")
        !jwtManager.parseToken("")
        !jwtManager.parseToken(null)
    }

    def 'should return a repo tokenDetails'() {
        when:
        def tokenManager = Mock(TokenManager)
        def jwtManager = new DefaultJwtManager(clock, env, tokenManager)

        def cromRepo = new CromRepo(randomNumber(), 1, randomNumber(), "repoName", randomNumber())
        def createdToken = createGeneratedToken(TokenType.REPOSITORY)
        def underlyingToken = new TokenManager.UderlyingTokenDetails(cromRepo.repoId, createdToken.id, TokenType.REPOSITORY)

        tokenManager.generateRepoToken(_, _) >> createdToken
        tokenManager.getTokenData(_, _) >> underlyingToken
        then:

        def token = jwtManager.createRepoToken(cromRepo)
        assert token

        def parsedToken = jwtManager.parseToken(token) as JwtTokenAuthentication.RepoJwtTokenAuthentication
        assert parsedToken
        parsedToken.repoId == cromRepo.repoId
        parsedToken.repoTokenId == createdToken.id
    }
}
