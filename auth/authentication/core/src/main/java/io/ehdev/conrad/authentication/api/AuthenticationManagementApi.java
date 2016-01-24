package io.ehdev.conrad.authentication.api;

import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface AuthenticationManagementApi {
    Authentication findAuthentication(ClientAuthenticationToken authenticationToken);
}
