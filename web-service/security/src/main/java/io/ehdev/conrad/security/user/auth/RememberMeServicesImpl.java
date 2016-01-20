package io.ehdev.conrad.security.user.auth;

import io.ehdev.conrad.api.user.database.BaseUserModel;
import io.ehdev.conrad.api.user.database.BaseUserRepository;
import io.ehdev.conrad.model.user.ConradUser;
import io.ehdev.conrad.security.database.model.SecurityUserTokenModel;
import io.ehdev.conrad.security.database.model.TokenType;
import io.ehdev.conrad.security.database.repositories.SecurityUserTokenModelRepository;
import io.ehdev.conrad.security.jwt.JwtManager;
import io.ehdev.conrad.security.jwt.UserToken;
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
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class RememberMeServicesImpl implements RememberMeServices {

    public static final Set<SimpleGrantedAuthority> ROLE_USER = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    private static final Logger logger = LoggerFactory.getLogger(RememberMeServicesImpl.class);

    private final SecurityUserTokenModelRepository tokenModelRepository;
    private final BaseUserRepository userRepository;
    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public RememberMeServicesImpl(SecurityUserTokenModelRepository tokenModelRepository,
                                  BaseUserRepository userRepository,
                                  UserCookieManger userCookieManger,
                                  JwtManager jwtManager) {
        this.tokenModelRepository = tokenModelRepository;
        this.userRepository = userRepository;
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        UserToken userToken = jwtManager.parseToken(userCookieManger.readCookieValue(request));
        SecurityUserTokenModel tokenModel = tokenModelRepository.findOne(UUID.fromString(userToken.getUniqueId()));

        if(tokenModel != null) {
            if(Objects.equals(tokenModel.getUserModel().getId().toString(), userToken.getUserId())) {
                return new RememberMeAuthenticationToken(tokenModel.getId().toString(), tokenModel.getUserModel().toUserModel(), ROLE_USER);
            }
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        UserToken userToken = jwtManager.parseToken(userCookieManger.readCookieValue(request));
        if(userToken != null) {
            SecurityUserTokenModel token = tokenModelRepository.findOne(UUID.fromString(userToken.getUniqueId()));
            token.setValid(false);
            tokenModelRepository.save(token);
        }
        userCookieManger.removeCookie(response);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        logger.info("Login Success: {}", auth);
        if(auth.getPrincipal() instanceof ConradUser) {
            addLogin((ConradUser) auth.getPrincipal(), response);
        }
    }

    private void addLogin(ConradUser user, HttpServletResponse response) {
        BaseUserModel baseUser = userRepository.findOne(user.getUuid());
        String token = jwtManager.createToken(baseUser, TokenType.USER, LocalDateTime.now().plusDays(30));
        userCookieManger.addCookie(token, response);
    }
}
