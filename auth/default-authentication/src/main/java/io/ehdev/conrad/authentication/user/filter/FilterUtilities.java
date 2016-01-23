package io.ehdev.conrad.authentication.user.filter;

import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class FilterUtilities {

    public static Authentication createAuthentication(ConradUser userPrincipal) {
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
}
