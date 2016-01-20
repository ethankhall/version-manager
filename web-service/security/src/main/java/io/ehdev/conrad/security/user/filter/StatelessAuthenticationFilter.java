package io.ehdev.conrad.security.user.filter;

import io.ehdev.conrad.api.user.database.BaseUserModel;
import io.ehdev.conrad.api.user.database.BaseUserRepository;
import io.ehdev.conrad.security.jwt.JwtManager;
import io.ehdev.conrad.security.jwt.UserToken;
import io.ehdev.conrad.security.user.auth.UserCookieManger;
import org.apache.commons.lang3.StringUtils;
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
import java.util.UUID;

@Component
public class StatelessAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);

    static final String HEADER_NAME = "X-AUTH-TOKEN";

    private final BaseUserRepository userRepository;
    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public StatelessAuthenticationFilter(BaseUserRepository userRepository,
                                         UserCookieManger userCookieManger,
                                         JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
    }

    String findTokenString(HttpServletRequest request) {
        if(request.getHeader(HEADER_NAME) != null) {
            return request.getHeader(HEADER_NAME);
        } else {
            return userCookieManger.readCookieValue(request);
        }
    }

    Authentication processUserToken(UserToken userToken) {
        if(userToken == null) {
            return null;
        }

        BaseUserModel user = userRepository.findOne(UUID.fromString(userToken.getUserId()));

        if(user == null) {
            return null;
        }

        logger.debug("Logging in {}", user.getId());
        return FilterUtilities.createAuthentication(user.toUserModel());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authentication: {}", authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            String tokenValue = findTokenString(request);

            logger.debug("tokenValue: {}", tokenValue);

            if (StringUtils.isNotBlank(tokenValue)) {
                UserToken userToken = jwtManager.parseToken(tokenValue);
                logger.debug("User token from cookie: {}", userToken);
                SecurityContextHolder.getContext().setAuthentication(processUserToken(userToken));
            }
        }
        filterChain.doFilter(request, response);
    }
}
