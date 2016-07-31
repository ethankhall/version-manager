package tech.crom.security.authentication.cookie

import org.springframework.stereotype.Service
import org.springframework.web.util.CookieGenerator
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class UserCookieMangerImpl : UserCookieManger {

    private val userCookieGenerator = CookieGenerator()

    init {
        this.userCookieGenerator.cookieName = "crom_cookie"
    }

    override fun addCookie(contents: String, response: HttpServletResponse) {
        userCookieGenerator.addCookie(response, contents)
    }

    override fun removeCookie(response: HttpServletResponse) {
        userCookieGenerator.addCookie(response, "")
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
