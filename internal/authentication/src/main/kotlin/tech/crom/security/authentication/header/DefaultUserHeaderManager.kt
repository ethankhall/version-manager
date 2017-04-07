package tech.crom.security.authentication.header

import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class DefaultUserHeaderManager : UserHeaderManager {

    override fun readHeaderValue(request: HttpServletRequest): String? {
        return request.getHeader("X-AUTH-TOKEN")
    }
}
