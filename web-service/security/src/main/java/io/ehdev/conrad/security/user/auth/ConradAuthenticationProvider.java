package io.ehdev.conrad.security.user.auth;

import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.security.database.repositories.UserModelRepository;
import io.ehdev.conrad.security.user.filter.FilterUtilities;
import io.ehdev.conrad.security.user.UserPrincipal;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ConradAuthenticationProvider implements AuthenticationProvider {

    private final ClientAuthenticationProvider clientAuthenticationProvider;
    private final UserModelRepository userModelRepository;

    public ConradAuthenticationProvider(ClientAuthenticationProvider clientAuthenticationProvider,
                                        UserModelRepository userModelRepository) {
        this.clientAuthenticationProvider = clientAuthenticationProvider;
        this.userModelRepository = userModelRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticate = clientAuthenticationProvider.authenticate(authentication);
        if(null != authenticate && authenticate instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken authenticationToken = (ClientAuthenticationToken) authenticate;
            UserProfile userProfile = authenticationToken.getUserProfile();
            UserModel user = userModelRepository.findOneByClientUserProfile(userProfile.getClass().getSimpleName(), userProfile.getId());
            FilterUtilities.createAuthentication(new UserPrincipal(user));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return clientAuthenticationProvider.supports(authentication);
    }

}
