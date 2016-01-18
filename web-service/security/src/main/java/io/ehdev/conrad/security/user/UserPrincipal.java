package io.ehdev.conrad.security.user;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.views.UserPublicView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserPrincipal implements SocialUserDetails {

    private UUID id;
    private String firstName;
    private String emailAddress;

    public UserPrincipal(UserModel userModel) {
        this(userModel.getId(), userModel.getFirstName(), userModel.getEmailAddress());
    }

    public UserPrincipal(UUID id, String firstName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }

    @JsonView(UserPublicView.class)
    public UUID getId() {
        return id;
    }

    @JsonView(UserPublicView.class)
    public String getFirstName() {
        return firstName;
    }

    @JsonView(UserPublicView.class)
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String getUserId() {
        return getId().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
