package io.ehdev.conrad.database.api.v2;


import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails;

import java.util.List;

public interface PermissionManagementApiV2 {

    ApiUserPermission findHighestUserPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails);

    boolean addPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails, ApiUserPermission permission);

    List<ApiUserPermissionDetails> getPermissions(ResourceDetails resourceDetails);
}
