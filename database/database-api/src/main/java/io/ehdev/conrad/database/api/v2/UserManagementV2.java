package io.ehdev.conrad.database.api.v2;

import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;

public interface UserManagementV2 {

    UserPermissionGrants getUserPermissions(AuthUserDetails authUserDetails);

    UserApiAuthentication createUser(String userName, String name, String email);
}
