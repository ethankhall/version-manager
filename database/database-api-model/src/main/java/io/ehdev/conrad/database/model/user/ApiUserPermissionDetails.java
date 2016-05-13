package io.ehdev.conrad.database.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class ApiUserPermissionDetails {
    @JsonPropertyDescription("The username for this permission")
    @JsonProperty("username")
    private final String username;

    @JsonPropertyDescription("The permission level granted to the user")
    @JsonProperty("permission")
    private final String permissions;

    public ApiUserPermissionDetails(String username, String permissions) {
        this.username = username;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public String getPermissions() {
        return permissions;
    }
}
