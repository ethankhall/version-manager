package io.ehdev.conrad.database.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class ApiRepoUserPermission {
    @JsonPropertyDescription("The username for this permission")
    @JsonProperty("username")
    String username;

    @JsonPropertyDescription("The permission level granted to the user")
    @JsonProperty("permission")
    String permissions;

    public ApiRepoUserPermission(String username, String permissions) {
        this.username = username;
        this.permissions = permissions;
    }

    public ApiRepoUserPermission() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
