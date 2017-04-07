package tech.crom.security.authentication.header

import javax.servlet.http.HttpServletRequest

interface UserHeaderManager {
    fun readHeaderValue(request: HttpServletRequest): String?
}
