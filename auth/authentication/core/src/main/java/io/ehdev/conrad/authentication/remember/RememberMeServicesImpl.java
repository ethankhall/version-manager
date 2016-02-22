package io.ehdev.conrad.authentication.remember;

import io.ehdev.conrad.authentication.cookie.UserCookieManger;
import io.ehdev.conrad.authentication.jwt.JwtManager;
import io.ehdev.conrad.database.api.TokenManagementApi;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.user.ApiToken;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class RememberMeServicesImpl implements RememberMeServices {

    public static final Set<SimpleGrantedAuthority> ROLE_USER = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    private static final Logger logger = LoggerFactory.getLogger(RememberMeServicesImpl.class);

    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;
    private final TokenManagementApi tokenManagementApi;

    @Autowired
    public RememberMeServicesImpl(UserCookieManger userCookieManger,
                                  JwtManager jwtManager,
                                  TokenManagementApi tokenManagementApi) {
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
        this.tokenManagementApi = tokenManagementApi;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        Optional<Pair<ApiTokenAuthentication, ApiToken>> pair = jwtManager.parseToken(userCookieManger.readCookieValue(request));

        if (pair.isPresent()) {
            ApiTokenAuthentication conradUser = pair.get().getKey();
            return new RememberMeAuthenticationToken(conradUser.getNiceName(), conradUser, ROLE_USER);
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        Optional<Pair<ApiTokenAuthentication, ApiToken>> pair = jwtManager.parseToken(userCookieManger.readCookieValue(request));
        if(pair.isPresent()) {
            tokenManagementApi.invalidateTokenValid(pair.get().getRight());
        }
        userCookieManger.removeCookie(response);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        logger.info("Login Success: {}", auth);
        if (auth.getPrincipal() instanceof ApiTokenAuthentication) {
            addLogin((ApiTokenAuthentication) auth.getPrincipal(), response);
        }
    }

    private void addLogin(ApiTokenAuthentication user, HttpServletResponse response) {
        String token = jwtManager.createToken(user);
        userCookieManger.addCookie(token, response);
    }
}
