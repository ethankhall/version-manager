package tech.crom.security.authentication.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import tech.crom.database.api.TokenManager
import tech.crom.logger.getLogger
import tech.crom.model.repository.CromRepo
import tech.crom.model.token.TokenType
import tech.crom.model.user.CromUser
import java.time.Clock
import java.time.ZonedDateTime
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class DefaultJwtManager @Autowired constructor(
    val clock: Clock,
    environment: Environment,
    val tokenManager: TokenManager) : JwtManager {

    private val log by getLogger()

    private val TOKEN_TYPE = "type"
    private val key: ByteArray

    init {
        this.key = environment.getRequiredProperty("jwt.signing.key").toByteArray()
    }

    override fun createUserToken(user: CromUser): String {
        val token = tokenManager.generateUserToken(user, ZonedDateTime.now(clock))
        return createToken(token)
    }

    override fun createRepoToken(repo: CromRepo): String {
        val token = tokenManager.generateRepoToken(repo, ZonedDateTime.now(clock))
        return createToken(token)
    }

    override fun createToken(tokenDetails: TokenManager.TokenDetails): String {
        val claims = Jwts
            .claims()
            .setSubject(tokenDetails.publicId)
            .setExpiration(Date.from(tokenDetails.expiresAt.toInstant()))
            .setNotBefore(Date.from(tokenDetails.createDate.toInstant()))

        claims.put(TOKEN_TYPE, tokenDetails.tokenType.name)

        return Jwts
            .builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, SecretKeySpec(key, "HmacSHA256"))
            .compact()
    }

    override fun parseToken(token: String?): JwtTokenAuthentication? {
        if (token == null || token.isBlank()) {
            log.debug("Token was blank")
            return null
        }

        try {
            val claimsJws = Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)

            val parsed = claimsJws.body

            val tokenString: String = parsed[TOKEN_TYPE] as String? ?: return null
            val tokenType = TokenType.valueOf(tokenString)

            val tokenId = parsed.subject
            val tokenData = tokenManager.getTokenData(tokenId, tokenType) ?: return null

            if (tokenData.tokenType == TokenType.REPOSITORY) {
                return JwtTokenAuthentication.RepoJwtTokenAuthentication(tokenData.linkedId, tokenData.privateId)
            } else {
                return JwtTokenAuthentication.UserJwtTokenAuthentication(tokenData.linkedId, tokenData.privateId)
            }
        } catch (exception: JwtException) {
            log.debug("Token {} was invalid: {}", token, exception.message)
            return null
        } catch (exception: IllegalArgumentException) {
            log.debug("Token {} was invalid: {}", token, exception.message)
            return null
        }
    }
}
