package io.ehdev.conrad.security.user.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelegatingSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private final SessionAuthenticationStrategy[] strategies;

    public DelegatingSessionAuthenticationStrategy(SessionAuthenticationStrategy... strategies) {
        this.strategies = strategies;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        for (SessionAuthenticationStrategy strategy : strategies) {
            strategy.onAuthentication(authentication, request, response);
        }
    }
}
