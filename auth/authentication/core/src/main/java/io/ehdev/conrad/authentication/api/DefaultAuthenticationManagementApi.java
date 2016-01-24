package io.ehdev.conrad.authentication.api;


import io.ehdev.conrad.authentication.database.model.SecurityUserClientProfileModel;
import io.ehdev.conrad.authentication.database.repositories.SecurityUserClientProfileModelRepository;
import io.ehdev.conrad.authentication.util.FilterUtilities;
import io.ehdev.conrad.database.api.internal.UserManagementApiInternal;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.impl.user.BaseUserModel;
import io.ehdev.conrad.model.user.ConradUser;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.profile.github.GitHubProfile;
import org.pac4j.oauth.profile.google2.Google2Profile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationManagementApi implements AuthenticationManagementApi {

    private final SecurityUserClientProfileModelRepository modelRepository;
    private final UserManagementApiInternal userManagementApi;

    @Autowired
    public DefaultAuthenticationManagementApi(SecurityUserClientProfileModelRepository modelRepository,
                                              UserManagementApiInternal userManagementApi) {
        this.modelRepository = modelRepository;
        this.userManagementApi = userManagementApi;
    }

    @Override
    public Authentication findAuthentication(ClientAuthenticationToken authenticationToken) {
        createNewUserIfNeeded(authenticationToken);
        UserProfile userProfile = authenticationToken.getUserProfile();
        BaseUserModel user = modelRepository.findOneByClientUserProfile(userProfile.getClass().getSimpleName(), userProfile.getId());
        ConradUser conradUser = ModelConversionUtility.toApiModel(user);
        return FilterUtilities.createAuthentication(conradUser);
    }

    private void createNewUserIfNeeded(ClientAuthenticationToken authentication) {
        UserProfile userProfile = authentication.getUserProfile();
        BaseUserModel userModel = modelRepository.findOneByClientUserProfile(
            userProfile.getClass().getSimpleName(), userProfile.getId());

        if (userModel == null) {
            UserView newUserModel = createNewUserModel(userProfile);
            userModel = userManagementApi.createInternalUser(newUserModel.name, newUserModel.email);

            SecurityUserClientProfileModel clientProfile = new SecurityUserClientProfileModel(userModel, userProfile.getClass().getSimpleName(), userProfile.getId());
            modelRepository.save(clientProfile);
        }
    }

    UserView createNewUserModel(UserProfile profile) {
        if(profile instanceof GitHubProfile) {
            return createUserModel((GitHubProfile) profile);
        } else if(profile instanceof Google2Profile) {
            return createUserModel((Google2Profile) profile);
        } else {
            throw new RuntimeException("Unsupported profile type: " + profile.getClass().getSimpleName());
        }
    }

    UserView createUserModel(GitHubProfile gitHubProfile) {
        return new UserView(gitHubProfile.getDisplayName(), gitHubProfile.getEmail());
    }

    UserView createUserModel(Google2Profile google2Profile) {
        return new UserView(google2Profile.getDisplayName(), google2Profile.getEmail());
    }

    private static class UserView {
        String name;
        String email;

        public UserView(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

}
