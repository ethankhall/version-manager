package io.ehdev.conrad.database.api;

import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import java.util.List;


public interface UserManagementApi {
    UserApiAuthentication createUser(String userName, String name, String email);

    List<ApiProjectDetails> findProjectsForUser(ApiTokenAuthentication authentication);
}
