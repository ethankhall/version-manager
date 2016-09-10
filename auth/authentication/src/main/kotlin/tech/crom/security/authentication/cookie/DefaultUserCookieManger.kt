package tech.crom.security.authentication.cookie

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.util.CookieGenerator
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
open class DefaultUserCookieManger @Autowired constructor(
    env: Environment
) : UserCookieManger {

    private val userCookieGenerator = CookieGenerator()
    private val domain: String

    init {
        this.userCookieGenerator.cookieName = "crom_cookie"
        domain = env.getRequiredProperty("auth.domain")
    }

    override fun addCookie(contents: String, response: HttpServletResponse) {
        userCookieGenerator.addCookie(response, contents)
    }

    override fun removeCookie(response: HttpServletResponse) {
        userCookieGenerator.removeCookie(response)
    }

    override fun readCookieValue(request: HttpServletRequest): String? {
        val cookies = request.cookies ?: return null
        for (cookie in cookies) {
            if (cookie.name == userCookieGenerator.cookieName) {
                return cookie.value
            }
        }
        return null
    }
}
