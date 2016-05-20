package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;

public interface UserManagementApi {

    UserPermissionGrants getUserPermissions(AuthUserDetails authUserDetails);

    UserApiAuthentication createUser(String userName, String name, String email);
}
