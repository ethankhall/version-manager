package io.ehdev.conrad.authentication.user.auth;

import io.ehdev.conrad.api.user.database.BaseUserModel;
import io.ehdev.conrad.api.user.database.BaseUserRepository;
import io.ehdev.conrad.authentication.database.model.SecurityUserClientProfileModel;
import io.ehdev.conrad.authentication.database.repositories.SecurityUserClientProfileModelRepository;
import io.ehdev.conrad.authentication.database.repositories.SecurityUserTokenModelRepository;
import io.ehdev.conrad.authentication.user.filter.FilterUtilities;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.github.GitHubProfile;
import org.pac4j.oauth.profile.google2.Google2Profile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ConradAuthenticationProvider implements AuthenticationProvider {

    private final BaseUserRepository userModelRepository;
    private final SecurityUserTokenModelRepository tokenModelRepository;
    private final ClientAuthenticationProvider clientAuthenticationProvider;
    private final SecurityUserClientProfileModelRepository clientUserProfileModelRepository;

    public ConradAuthenticationProvider(BaseUserRepository userModelRepository,
                                        ClientAuthenticationProvider clientAuthenticationProvider,
                                        SecurityUserTokenModelRepository tokenModelRepository,
                                        SecurityUserClientProfileModelRepository clientUserProfileModelRepository) {
        this.userModelRepository = userModelRepository;
        this.clientAuthenticationProvider = clientAuthenticationProvider;
        this.tokenModelRepository = tokenModelRepository;
        this.clientUserProfileModelRepository = clientUserProfileModelRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticate = clientAuthenticationProvider.authenticate(authentication);
        if(null != authenticate && authenticate instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken authenticationToken = (ClientAuthenticationToken) authenticate;
            createNewUserIfNeeded(authenticationToken);
            UserProfile userProfile = authenticationToken.getUserProfile();
            BaseUserModel user = tokenModelRepository.findOneByClientUserProfile(userProfile.getClass().getSimpleName(), userProfile.getId());
            return FilterUtilities.createAuthentication(user.toUserModel());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return clientAuthenticationProvider.supports(authentication);
    }


    private void createNewUserIfNeeded(ClientAuthenticationToken authentication) {
        UserProfile userProfile = authentication.getUserProfile();
        BaseUserModel userModel = tokenModelRepository.findOneByClientUserProfile(
            userProfile.getClass().getSimpleName(), userProfile.getId());

        if (userModel == null) {
            userModel = userModelRepository.save(createNewUserModel(userProfile));

            SecurityUserClientProfileModel clientProfile = new SecurityUserClientProfileModel(userModel, userProfile.getClass().getSimpleName(), userProfile.getId());
            clientUserProfileModelRepository.save(clientProfile);
        }
    }

    BaseUserModel createNewUserModel(UserProfile profile) {
        if(profile instanceof GitHubProfile) {
            return createUserModel((GitHubProfile) profile);
        } else if(profile instanceof Google2Profile) {
            return createUserModel((Google2Profile) profile);
        } else {
            throw new RuntimeException("Unsupported profile type: " + profile.getClass().getSimpleName());
        }
    }

    BaseUserModel createUserModel(GitHubProfile gitHubProfile) {
        return new BaseUserModel(gitHubProfile.getDisplayName(), gitHubProfile.getEmail());
    }

    BaseUserModel createUserModel(Google2Profile google2Profile) {
        return new BaseUserModel(google2Profile.getDisplayName(), google2Profile.getEmail());
    }

}
