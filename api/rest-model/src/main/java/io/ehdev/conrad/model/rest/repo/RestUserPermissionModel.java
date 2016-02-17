package io.ehdev.conrad.model.rest.repo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class RestUserPermissionModel {

    @JsonPropertyDescription("Username of the user.")
    @JsonProperty("username")
    String username;

    @JsonPropertyDescription("Permission granted to the user.")
    @JsonProperty("permission")
    String permission;

    public RestUserPermissionModel() {
    }

    public RestUserPermissionModel(String username, String permission) {
        this.username = username;
        this.permission = permission;
    }
}
