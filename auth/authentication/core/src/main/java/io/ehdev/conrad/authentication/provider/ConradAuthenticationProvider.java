package io.ehdev.conrad.authentication.provider;

import io.ehdev.conrad.authentication.api.AuthenticationManagementApi;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ConradAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(ConradAuthenticationProvider.class);
    private final ClientAuthenticationProvider clientAuthenticationProvider;
    private final AuthenticationManagementApi authenticationManagementApi;

    public ConradAuthenticationProvider(ClientAuthenticationProvider clientAuthenticationProvider,
                                        AuthenticationManagementApi authenticationManagementApi) {
        this.clientAuthenticationProvider = clientAuthenticationProvider;
        this.authenticationManagementApi = authenticationManagementApi;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticate = clientAuthenticationProvider.authenticate(authentication);
        logger.info("Auth: {}", authenticate);
        if(null != authenticate && authenticate instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken authenticationToken = (ClientAuthenticationToken) authenticate;
            return authenticationManagementApi.findAuthentication(authenticationToken);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return clientAuthenticationProvider.supports(authentication);
    }

}
