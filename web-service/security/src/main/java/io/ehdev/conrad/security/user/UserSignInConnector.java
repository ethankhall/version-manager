package io.ehdev.conrad.security.user;

import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.security.database.repositories.UserModelRepository;
import io.ehdev.conrad.security.jwt.JwtManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserSignInConnector implements ConnectionSignUp, SignInAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserSignInConnector.class);

    private final UserModelRepository userRepository;
    private final UserCookieManger userCookieManger;
    private final JwtManager jwtManager;

    @Autowired
    public UserSignInConnector(UserModelRepository userRepository,
                               UserCookieManger userCookieManger,
                               JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.userCookieManger = userCookieManger;
        this.jwtManager = jwtManager;
    }

    @Override
    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        UserModel userModel = userRepository.save(createUserModel(userProfile));
        return userModel.getId().toString();
    }

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        logger.info("Signed In: {} {}", userId, connection.getKey());
        UserModel user = userRepository.findOne(UUID.fromString(userId));
        String userToken = jwtManager.createUserToken(user, LocalDateTime.now().plusDays(30));
        userCookieManger.addCookie(userToken, request.getNativeResponse(HttpServletResponse.class));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            new UserPrincipal(user), null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }

    private UserModel createUserModel(UserProfile userProfile) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(userProfile.getFirstName());
        userModel.setEmailAddress(userProfile.getEmail());
        return userModel;
    }
}
