package io.ehdev.conrad.database.model.user;

public class ApiRepoUserPermission {
    String username;
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
