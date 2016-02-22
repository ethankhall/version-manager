package io.ehdev.conrad.service.api.service.model.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PermissionGrant {

    @JsonProperty("username")
    String username;

    @JsonProperty("permission")
    String permission;

    public PermissionGrant(String username, String permission) {
        this.username = username;
        this.permission = permission;
    }

    public PermissionGrant() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
