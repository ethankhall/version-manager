package tech.crom.security.authentication.jwt

import java.util.*

interface  JwtTokenAuthentication {
    data class UserJwtTokenAuthentication(val userUuid: UUID, val userTokenUid: UUID): JwtTokenAuthentication
    data class RepoJwtTokenAuthentication(val repoUuid: UUID, val repoTokenUid: UUID): JwtTokenAuthentication
}
