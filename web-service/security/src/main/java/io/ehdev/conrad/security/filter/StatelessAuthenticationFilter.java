package io.ehdev.conrad.security.filter;

import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.security.database.repositories.UserModelRepository;
import io.ehdev.conrad.security.jwt.JwtManager;
import io.ehdev.conrad.security.jwt.UserToken;
import io.ehdev.conrad.security.user.UserCookieManger;
import io.ehdev.conrad.security.user.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StatelessAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);

    static final String HEADER_NAME = "X-AUTH-TOKEN";

    private final UserModelRepository userModelRepository;
    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public StatelessAuthenticationFilter(UserModelRepository userModelRepository,
                                         UserCookieManger userCookieManger,
                                         JwtManager jwtManager) {
        this.userModelRepository = userModelRepository;
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = findTokenString(request);

        logger.debug("tokenValue: {}", tokenValue);

        if (StringUtils.isNotBlank(tokenValue)) {
            UserToken userToken = jwtManager.parseToken(tokenValue);
            logger.debug("User token from cookie: {}", userToken);
            processUserToken(userToken);
        }
        filterChain.doFilter(request, response);
    }

    String findTokenString(HttpServletRequest request) {
        if(request.getHeader(HEADER_NAME) != null) {
            return request.getHeader(HEADER_NAME);
        } else {
            return userCookieManger.readCookieValue(request);
        }
    }

    void processUserToken(UserToken userToken) {
        if(userToken == null) {
            return;
        }

        UserModel user = userModelRepository.findOne(UUID.fromString(userToken.getUserId()));

        if(user == null) {
            return;
        }

        logger.debug("Logging in {}", user.getId());
        UserPrincipal userPrincipal = new UserPrincipal(user);
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
