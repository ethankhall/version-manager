package io.ehdev.conrad.database.model.repo.details;

import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

import java.util.Objects;
import java.util.UUID;

public class AuthUserDetails {
    private final UUID userId;
    private final ApiUserPermission permission;
    private final String name;
    private final ApiTokenAuthentication tokenAuthentication;

    public AuthUserDetails(UUID userId, String name, ApiUserPermission permission, ApiTokenAuthentication tokenAuthentication) {
        this.name = name;
        this.tokenAuthentication = tokenAuthentication;
        Objects.nonNull(userId);
        this.userId = userId;
        this.permission = permission;
    }

    public UUID getUserId() {
        return userId;
    }

    public ApiUserPermission getPermission() {
        return permission;
    }

    public boolean isAuthenticationUser() {
        return tokenAuthentication instanceof UserApiAuthentication;
    }

    public ApiTokenAuthentication getTokenAuthentication() {
        return tokenAuthentication;
    }

    public String getName() {
        return name;
    }
}
