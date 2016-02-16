package io.ehdev.conrad.authentication.filter

import io.ehdev.conrad.authentication.cookie.UserCookieManger
import io.ehdev.conrad.authentication.jwt.JwtManager
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class StatelessAuthenticationFilterTest extends Specification {

    StatelessAuthenticationFilter filter
    UserCookieManger mockCookie
    JwtManager mockJwt

    def setup() {
        mockCookie = Mock(UserCookieManger)
        mockJwt = Mock(JwtManager)
        filter = new StatelessAuthenticationFilter(mockCookie, mockJwt)
    }

    def 'will pull from X-AUTH-HEADER if it is set'() {
        def response = new MockHttpServletRequest()
        response.addHeader(StatelessAuthenticationFilter.HEADER_NAME, 'abc123')

        when:
        def token = filter.getToken(response)

        then:
        token == 'abc123'
    }

    def 'will pull from cookie if header is not set'() {
        def response = new MockHttpServletRequest()

        when:
        def token = filter.getToken(response)

        then:
        1 * mockCookie.readCookieValue(response) >> 'def456'
        token == 'def456'
    }
}
