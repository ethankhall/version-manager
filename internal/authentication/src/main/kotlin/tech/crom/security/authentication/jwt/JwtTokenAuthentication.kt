package tech.crom.security.authentication.jwt

interface JwtTokenAuthentication {
    data class UserJwtTokenAuthentication(val userId: Long, val userTokenId: Long) : JwtTokenAuthentication
    data class RepoJwtTokenAuthentication(val repoId: Long, val repoTokenId: Long) : JwtTokenAuthentication
}
