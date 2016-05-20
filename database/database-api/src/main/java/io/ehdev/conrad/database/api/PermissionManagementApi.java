package io.ehdev.conrad.database.api;


import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;

import java.util.List;
import java.util.UUID;

public interface PermissionManagementApi {

    ApiUserPermission findHighestUserPermission(UUID userId, ResourceDetails resourceDetails);

    ApiUserPermission findHighestUserPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails);

    boolean addPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails, ApiUserPermission permission);

    boolean addPermission(String userName, ResourceDetails resourceDetails, ApiUserPermission permission);

    List<ApiUserPermissionDetails> getPermissions(ResourceDetails resourceDetails);

    UserPermissionGrants getPermissions(AuthUserDetails authUserDetails);
}
