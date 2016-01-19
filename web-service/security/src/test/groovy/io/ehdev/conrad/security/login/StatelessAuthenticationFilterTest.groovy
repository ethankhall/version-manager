package io.ehdev.conrad.security.login

import io.ehdev.conrad.security.database.repositories.UserModelRepository
import io.ehdev.conrad.security.jwt.JwtManager
import io.ehdev.conrad.security.user.auth.UserCookieMangerImpl
import io.ehdev.conrad.security.user.filter.StatelessAuthenticationFilter
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

import javax.servlet.http.Cookie

class StatelessAuthenticationFilterTest extends Specification {

    UserModelRepository userModelRepository
    UserCookieMangerImpl userCookieManger = new UserCookieMangerImpl()
    JwtManager jwtManager
    StatelessAuthenticationFilter filter

    def setup() {
        userModelRepository = Mock(UserModelRepository)
        jwtManager = Mock(JwtManager)
        filter = new StatelessAuthenticationFilter(userModelRepository, userCookieManger, jwtManager)
    }

    def 'test filter getting header'() {
        when:
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(StatelessAuthenticationFilter.HEADER_NAME, 'token')

        then:
        filter.findTokenString(request) == 'token'
    }

    def 'test filter getting cookie'() {
        when:
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie(UserCookieMangerImpl.COOKIE_NAME, "cookie"))

        then:
        filter.findTokenString(request) == 'cookie'
    }
}
