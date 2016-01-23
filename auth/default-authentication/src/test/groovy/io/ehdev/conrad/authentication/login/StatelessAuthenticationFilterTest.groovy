package io.ehdev.conrad.authentication.login

import io.ehdev.conrad.authentication.database.repositories.SecurityUserModelRepository
import io.ehdev.conrad.authentication.jwt.JwtManager
import io.ehdev.conrad.authentication.user.auth.UserCookieMangerImpl
import io.ehdev.conrad.authentication.user.filter.StatelessAuthenticationFilter
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

import javax.servlet.http.Cookie

class StatelessAuthenticationFilterTest extends Specification {

    SecurityUserModelRepository userModelRepository
    UserCookieMangerImpl userCookieManger = new UserCookieMangerImpl()
    JwtManager jwtManager
    StatelessAuthenticationFilter filter

    def setup() {
        userModelRepository = Mock(SecurityUserModelRepository)
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
