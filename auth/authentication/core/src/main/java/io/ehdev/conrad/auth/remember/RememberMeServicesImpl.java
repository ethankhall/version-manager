package io.ehdev.conrad.auth.remember;

import io.ehdev.conrad.auth.cookie.UserCookieManger;
import io.ehdev.conrad.auth.jwt.JwtManager;
import io.ehdev.conrad.model.user.ConradToken;
import io.ehdev.conrad.model.user.ConradUser;
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
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RememberMeServicesImpl implements RememberMeServices {

    public static final Set<SimpleGrantedAuthority> ROLE_USER = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    private static final Logger logger = LoggerFactory.getLogger(RememberMeServicesImpl.class);

    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public RememberMeServicesImpl(UserCookieManger userCookieManger,
                                  JwtManager jwtManager) {
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {

        Optional<ConradUser> user = jwtManager.findUser(userCookieManger.readCookieValue(request));

        if (user.isPresent()) {
            ConradUser conradUser = user.get();
            return new RememberMeAuthenticationToken(conradUser.getUuid().toString(), conradUser, ROLE_USER);
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        ConradToken token = jwtManager.findToken(userCookieManger.readCookieValue(request));
        UserToken userToken = jwtManager.parseToken(userCookieManger.readCookieValue(request));
        if (userToken != null) {
            SecurityUserTokenModel token = tokenModelRepository.findOne(UUID.fromString(userToken.getUniqueId()));
            token.setValid(false);
            tokenModelRepository.save(token);
        }
        userCookieManger.removeCookie(response);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        logger.info("Login Success: {}", auth);
        if (auth.getPrincipal() instanceof ConradUser) {
            addLogin((ConradUser) auth.getPrincipal(), response);
        }
    }

    private void addLogin(ConradUser user, HttpServletResponse response) {
        BaseUserModel baseUser = userRepository.findOne(user.getUuid());
        String token = jwtManager.createToken(baseUser, TokenType.USER, LocalDateTime.now().plusDays(30));
        userCookieManger.addCookie(token, response);
    }
}
