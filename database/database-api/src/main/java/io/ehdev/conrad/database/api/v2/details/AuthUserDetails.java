package io.ehdev.conrad.database.api.v2.details;

import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

import java.util.Objects;
import java.util.UUID;

public class AuthUserDetails {
    private final UUID userId;
    private final ApiUserPermission permission;
    private final ApiTokenAuthentication tokenAuthentication;

    public AuthUserDetails(UUID userId, ApiUserPermission permission, ApiTokenAuthentication tokenAuthentication) {
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
}
