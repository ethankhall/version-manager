package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.user.ApiUser;

public interface UserManagementApi {
    ApiUser createUser(String userName, String name, String email);
}
