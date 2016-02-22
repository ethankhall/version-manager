package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.permission.UserApiAuthentication;

public interface UserManagementApi {
    UserApiAuthentication createUser(String userName, String name, String email);
}
