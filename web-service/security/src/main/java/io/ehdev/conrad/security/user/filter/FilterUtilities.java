package io.ehdev.conrad.security.user.filter;

import io.ehdev.conrad.security.user.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class FilterUtilities {

    public static Authentication createAuthentication(UserPrincipal userPrincipal) {
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
}
