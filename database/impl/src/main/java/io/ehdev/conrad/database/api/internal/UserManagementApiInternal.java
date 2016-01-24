package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.UserManagementApi;
import io.ehdev.conrad.database.impl.user.BaseUserModel;

public interface UserManagementApiInternal extends UserManagementApi {

    BaseUserModel createInternalUser(String name, String email);
}
