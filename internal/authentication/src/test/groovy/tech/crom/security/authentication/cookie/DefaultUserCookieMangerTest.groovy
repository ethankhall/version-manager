package tech.crom.security.authentication.cookie

import org.springframework.mock.env.MockEnvironment
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

import javax.servlet.http.Cookie

class DefaultUserCookieMangerTest extends Specification {

    def 'should add cookie to request when asked'() {
        when:
        def cookieManager = createCookieManager()
        def request = new MockHttpServletResponse()
        cookieManager.addCookie("hello!", request)

        then:
        request.getCookie("crom_cookie")?.value == "hello!"
    }

    def 'should remove cookie when asked'() {
        when:
        def cookieManager = createCookieManager()
        def request = new MockHttpServletResponse()
        request.addCookie(new Cookie("crom_cookie", "hello!"))

        cookieManager.removeCookie(request)

        then:
        //working around bug in MockHttpServlet
        request.cookies.length == 2
        request.cookies[0].value == "hello!"
        request.cookies[1].value == ""
    }

    def 'should be able to find cookie from request'() {
        when:
        def cookieManager = createCookieManager()
        def request = new MockHttpServletRequest()
        request.setCookies(new Cookie("crom_cookie", "hello!"))

        then:
        cookieManager.readCookieValue(request) ==  "hello!"
    }

    DefaultUserCookieManger createCookieManager() {
        def environment = new MockEnvironment()
        environment.setProperty("auth.domain", "localhost")
        return new DefaultUserCookieManger(environment)
    }
}
