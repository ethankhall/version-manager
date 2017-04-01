package tech.crom.security.authentication.cookie

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.util.CookieGenerator
import tech.crom.logger.getLogger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
open class DefaultUserCookieManger @Autowired constructor(
    env: Environment
) : UserCookieManger {

    private val logger by getLogger()
    private val userCookieGenerator = CookieGenerator()

    init {
        this.userCookieGenerator.cookieName = "crom_cookie"
        this.userCookieGenerator.cookieDomain = env.getRequiredProperty("auth.domain")
        logger.info("Using domain `{}`", userCookieGenerator.cookieDomain)
    }

    override fun addCookie(contents: String, response: HttpServletResponse) {
        userCookieGenerator.addCookie(response, contents)
    }

    override fun removeCookie(response: HttpServletResponse) {
        userCookieGenerator.removeCookie(response)
    }

    override fun readCookieValue(request: HttpServletRequest): String? {
        val cookies = request.cookies ?: return null
        return cookies
            .firstOrNull { it.name == userCookieGenerator.cookieName }
            ?.value
    }
}
