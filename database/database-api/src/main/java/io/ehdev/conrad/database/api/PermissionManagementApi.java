package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.user.ApiUser;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

public interface PermissionManagementApi {

    boolean doesUserHavePermission(ApiUser apiUser, String project, String repoName, ApiUserPermission permission);

    boolean addPermission(String username, ApiUser authenticatedUser, String projectName, String repoName, ApiUserPermission permission);
}
