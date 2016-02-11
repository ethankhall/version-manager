package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.user.ApiUser;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

public interface PermissionManagementApi {

    boolean doesUserHavePermission(ApiUser apiUser, ApiRepoModel qualifiedRepo, ApiUserPermission permission);

    boolean doesUserHavePermission(ApiUser apiUser, String project, ApiUserPermission permission);
}
