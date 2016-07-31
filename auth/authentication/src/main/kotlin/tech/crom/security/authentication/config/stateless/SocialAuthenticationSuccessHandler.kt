package tech.crom.security.authentication.config.stateless

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Service
import tech.crom.database.api.TokenManager
import tech.crom.database.api.UserManager
import tech.crom.security.authentication.cookie.UserCookieManger
import tech.crom.security.authentication.jwt.JwtManager
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class SocialAuthenticationSuccessHandler @Autowired constructor(
    val userManager: UserManager,
    val tokenManager: TokenManager,
    val jwtManager: JwtManager,
    val userCookieManger: UserCookieManger): SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, auth: Authentication?) {
        logger.info("onAuthenticationSuccess")
        if (auth != null && response != null) {
            addAuthToRequest(auth, response)
        }
        super.onAuthenticationSuccess(request, response, auth)
    }

    private fun addAuthToRequest(auth: Authentication, response: HttpServletResponse) {
        val user = userManager.findUserDetails(auth.name)
        val token = tokenManager.generateUserToken(user!!, LocalDateTime.now().plusDays(60))
        val jwtToken = jwtManager.createToken(token)

        userCookieManger.addCookie(jwtToken, response)
    }
}
