package io.ehdev.conrad.security.user.auth;

import io.ehdev.conrad.security.database.model.SecurityUserModel;
import io.ehdev.conrad.security.database.model.SecurityUserTokenModel;
import io.ehdev.conrad.security.database.model.TokenType;
import io.ehdev.conrad.security.database.repositories.SecurityUserModelRepository;
import io.ehdev.conrad.security.database.repositories.SecurityUserTokenModelRepository;
import io.ehdev.conrad.security.jwt.JwtManager;
import io.ehdev.conrad.security.jwt.UserToken;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
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

    private final SecurityUserModelRepository userModelRepository;
    private final SecurityUserTokenModelRepository userTokenModelRepository;
    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public RememberMeServicesImpl(SecurityUserModelRepository userModelRepository,
                                  SecurityUserTokenModelRepository userTokenModelRepository,
                                  UserCookieManger userCookieManger, JwtManager jwtManager) {
        this.userTokenModelRepository = userTokenModelRepository;
        this.userCookieManger = userCookieManger;

        this.userModelRepository = userModelRepository;
        this.jwtManager = jwtManager;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        UserToken userToken = jwtManager.parseToken(userCookieManger.readCookieValue(request));
        SecurityUserTokenModel tokenModel = userTokenModelRepository.findOne(UUID.fromString(userToken.getUniqueId()));

        if(tokenModel != null) {
            if(Objects.equals(tokenModel.getUserModel().getId().toString(), userToken.getUserId())) {
                return new RememberMeAuthenticationToken(tokenModel.getId().toString(), tokenModel.getUserModel().toUserModel(), ROLE_USER);
            }
        }
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        userCookieManger.removeCookie(response);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        if(successfulAuthentication instanceof ClientAuthenticationToken) {
            addLogin((ClientAuthenticationToken) successfulAuthentication, response);
        }
    }

    private void addLogin(ClientAuthenticationToken successfulAuthentication, HttpServletResponse response) {
        UserProfile userProfile = successfulAuthentication.getUserProfile();
        SecurityUserModel user = userModelRepository.findOneByClientUserProfile(userProfile.getClass().getSimpleName(), userProfile.getId());
        String token = jwtManager.createToken(user, TokenType.USER, LocalDateTime.now().plusDays(30));
        userCookieManger.addCookie(token, response);
    }
}
