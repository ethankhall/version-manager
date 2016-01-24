package io.ehdev.conrad.authentication.filter;

import io.ehdev.conrad.authentication.cookie.UserCookieManger;
import io.ehdev.conrad.authentication.jwt.JwtManager;
import io.ehdev.conrad.authentication.util.FilterUtilities;
import io.ehdev.conrad.model.user.ConradToken;
import io.ehdev.conrad.model.user.ConradUser;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class StatelessAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);

    static final String HEADER_NAME = "X-AUTH-TOKEN";

    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public StatelessAuthenticationFilter(UserCookieManger userCookieManger,
                                         JwtManager jwtManager) {
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
    }

    public String getToken(HttpServletRequest request) {
        if (request.getHeader(HEADER_NAME) == null) {
            return request.getHeader(HEADER_NAME);
        } else {
            return userCookieManger.readCookieValue(request);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authentication: {}", authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            Optional<Pair<ConradUser, ConradToken>> pair = jwtManager.parseToken(getToken(request));

            if(pair.isPresent()) {
                ConradUser user = pair.get().getLeft();
                logger.debug("User token from cookie: {}", user);
                SecurityContextHolder.getContext().setAuthentication(FilterUtilities.createAuthentication(user));
            }
        }
        filterChain.doFilter(request, response);
    }
}
