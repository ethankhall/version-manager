package io.ehdev.conrad.security.user.create;

import io.ehdev.conrad.security.database.model.SecurityUserClientProfileModel;
import io.ehdev.conrad.security.database.model.SecurityUserModel;
import io.ehdev.conrad.security.database.repositories.SecurityUserClientProfileModelRepository;
import io.ehdev.conrad.security.database.repositories.SecurityUserModelRepository;
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

    private final SecurityUserModelRepository userModelRepository;
    private final SecurityUserClientProfileModelRepository clientUserProfileModelRepository;

    @Autowired
    public CreateNewUserSessionStrategy(SecurityUserModelRepository userModelRepository, SecurityUserClientProfileModelRepository clientUserProfileModelRepository) {
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
        SecurityUserModel userModel = userModelRepository.findOneByClientUserProfile(
            userProfile.getClass().getSimpleName(), userProfile.getId());

        if (userModel == null) {
            userModel = userModelRepository.save(createNewUserModel(userProfile));

            SecurityUserClientProfileModel clientProfile = new SecurityUserClientProfileModel(userModel, userProfile.getClass().getSimpleName(), userProfile.getId());
            clientUserProfileModelRepository.save(clientProfile);
        }
    }

    SecurityUserModel createNewUserModel(UserProfile profile) {
        if(profile instanceof GitHubProfile) {
            return createUserModel((GitHubProfile) profile);
        } else if(profile instanceof Google2Profile) {
            return createUserModel((Google2Profile) profile);
        } else {
            throw new RuntimeException("Unsupported profile type: " + profile.getClass().getSimpleName());
        }
    }

    SecurityUserModel createUserModel(GitHubProfile gitHubProfile) {
        return new SecurityUserModel(gitHubProfile.getDisplayName(), gitHubProfile.getEmail());
    }

    SecurityUserModel createUserModel(Google2Profile google2Profile) {
        return new SecurityUserModel(google2Profile.getDisplayName(), google2Profile.getEmail());
    }
}
