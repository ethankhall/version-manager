package tech.crom.security.authentication.jwt

import tech.crom.database.api.TokenManager
import tech.crom.model.repository.CromRepo
import tech.crom.model.user.CromUser


interface JwtManager {

    fun createUserToken(user: CromUser): String

    fun createRepoToken(repo: CromRepo): String

    fun createToken(token: TokenManager.GeneratedToken): String

    fun parseToken(token: String): JwtTokenAuthentication?

}
