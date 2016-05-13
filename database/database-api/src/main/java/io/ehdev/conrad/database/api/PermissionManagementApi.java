package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

import io.ehdev.conrad.database.model.user.UserPermissionGrants;
import java.util.List;

public interface PermissionManagementApi {

    ApiUserPermission findHighestUserPermission(ApiTokenAuthentication apiUser, String project, String repoName);

    boolean doesUserHavePermission(ApiTokenAuthentication apiUser, String project, String repoName, ApiUserPermission permission);

    boolean addPermission(String username, ApiTokenAuthentication authenticatedUser, String projectName, String repoName, ApiUserPermission permission);

    boolean forceAddPermission(String username, String projectName, String repoName, ApiUserPermission permission);

    List<ApiUserPermissionDetails> getPermissions(ApiParameterContainer repoModel);

    UserPermissionGrants getUserPermissions(ApiTokenAuthentication authenticatedUser);
}
