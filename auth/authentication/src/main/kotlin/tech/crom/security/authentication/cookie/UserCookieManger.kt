package tech.crom.security.authentication.cookie

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface UserCookieManger {

    fun addCookie(contents: String, response: HttpServletResponse)

    fun removeCookie(response: HttpServletResponse)

    fun readCookieValue(request: HttpServletRequest): String?
}
