package tech.crom.security.authentication.cookie

import org.jetbrains.spek.api.Spek
import org.springframework.mock.env.MockEnvironment
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import javax.servlet.http.Cookie
import kotlin.test.assertEquals

class DefaultUserCookieMangerTest : Spek({

    val environment = MockEnvironment()
    environment.setProperty("auth.domain", "localhost") 
    val cookieManager = DefaultUserCookieManger(environment)

    on("cookie manager") {
        it("should add cookie to request when asked") {
            val request = MockHttpServletResponse()
            cookieManager.addCookie("hello!", request)

            assertEquals(request.getCookie("crom_cookie")?.value, "hello!")
        }

        it("should remove cookie when asked") {
            val request = MockHttpServletResponse()
            request.addCookie(Cookie("crom_cookie", "hello!"))

            cookieManager.removeCookie(request)

            //working around bug in MockHttpServlet
            assertEquals(request.cookies.size, 2)
            assertEquals(request.cookies[0].value, "hello!")
            assertEquals(request.cookies[1].value, "")
        }

        it("should be able to find cookie from request") {
            val request = MockHttpServletRequest()
            request.setCookies(Cookie("crom_cookie", "hello!"))

            assertEquals(cookieManager.readCookieValue(request), "hello!")
        }
    }
})
