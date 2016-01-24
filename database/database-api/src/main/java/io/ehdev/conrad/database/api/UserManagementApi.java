package io.ehdev.conrad.database.api;

import io.ehdev.conrad.model.user.ConradUser;

public interface UserManagementApi {
    ConradUser createUser(String name, String email);
}
