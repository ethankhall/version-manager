package tech.crom.security.authentication.config.stateless

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.filter.GenericFilterBean
import tech.crom.database.api.RepoManager
import tech.crom.database.api.UserManager
import tech.crom.model.security.authentication.CromRepositoryAuthentication
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.security.authentication.cookie.UserCookieManger
import tech.crom.security.authentication.header.UserHeaderManager
import tech.crom.security.authentication.jwt.JwtManager
import tech.crom.security.authentication.jwt.JwtTokenAuthentication
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Service
class StatelessAuthenticationFilter @Autowired constructor(
    val cookieManager: UserCookieManger,
    val jwtManager: JwtManager,
    val userManager: UserManager,
    val repoManager: RepoManager,
    val userHeaderManager: UserHeaderManager) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        setAuthenticationFromHeader(request as HttpServletRequest)
        chain.doFilter(request, response)
    }

    private fun setAuthenticationFromHeader(request: HttpServletRequest) {
        val responseValue = cookieManager.readCookieValue(request) ?: userHeaderManager.readHeaderValue(request)
        val cookieValue = responseValue ?: return
        val token = jwtManager.parseToken(cookieValue) ?: return

        if (token is JwtTokenAuthentication.UserJwtTokenAuthentication) {
            val user = userManager.findUserDetails(token.userId) ?: return
            SecurityContextHolder.getContext().authentication = CromUserAuthentication(user)
        } else if (token is JwtTokenAuthentication.RepoJwtTokenAuthentication) {
            val repo = repoManager.findRepo(token.repoId) ?: return
            SecurityContextHolder.getContext().authentication = CromRepositoryAuthentication(repo)
        }
    }
}
