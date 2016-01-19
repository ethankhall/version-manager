package io.ehdev.conrad.security.user.create;

import io.ehdev.conrad.security.database.model.ClientUserProfileModel;
import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.security.database.repositories.ClientUserProfileModelRepository;
import io.ehdev.conrad.security.database.repositories.UserModelRepository;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.github.GitHubProfile;
import org.pac4j.oauth.profile.google2.Google2Profile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CreateNewUserSessionStrategy implements SessionAuthenticationStrategy {

    private final UserModelRepository userModelRepository;
    private final ClientUserProfileModelRepository clientUserProfileModelRepository;

    @Autowired
    public CreateNewUserSessionStrategy(UserModelRepository userModelRepository, ClientUserProfileModelRepository clientUserProfileModelRepository) {
        this.userModelRepository = userModelRepository;
        this.clientUserProfileModelRepository = clientUserProfileModelRepository;
    }


    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        if(authentication instanceof ClientAuthenticationToken) {
            checkAuthentication((ClientAuthenticationToken) authentication);
        }
    }

    private void checkAuthentication(ClientAuthenticationToken authentication) {
        UserProfile userProfile = authentication.getUserProfile();
        UserModel userModel = userModelRepository.findOneByClientUserProfile(
            userProfile.getClass().getSimpleName(), userProfile.getId());

        if (userModel == null) {
            userModel = userModelRepository.save(createNewUserModel(userProfile));

            ClientUserProfileModel clientProfile = new ClientUserProfileModel(userModel, userProfile.getClass().getSimpleName(), userProfile.getId());
            clientUserProfileModelRepository.save(clientProfile);
        }
    }

    UserModel createNewUserModel(UserProfile profile) {
        if(profile instanceof GitHubProfile) {
            return createUserModel((GitHubProfile) profile);
        } else if(profile instanceof Google2Profile) {
            return createUserModel((Google2Profile) profile);
        } else {
            throw new RuntimeException("Unsupported profile type: " + profile.getClass().getSimpleName());
        }
    }

    UserModel createUserModel(GitHubProfile gitHubProfile) {
        return new UserModel(gitHubProfile.getDisplayName(), gitHubProfile.getEmail());
    }

    UserModel createUserModel(Google2Profile google2Profile) {
        return new UserModel(google2Profile.getDisplayName(), google2Profile.getEmail());
    }
}
