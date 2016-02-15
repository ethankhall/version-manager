package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

public interface PermissionManagementApiInternal extends PermissionManagementApi {

    boolean forceAddPermission(String username, String projectName, String repoName, ApiUserPermission permission);
}
