package io.ehdev.conrad.database.api.v2;

import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;

public class RequestDetails {

    private final ResourceDetails resourceDetails;
    private final AuthUserDetails authUserDetails;

    public RequestDetails(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) {
        this.resourceDetails = resourceDetails;
        this.authUserDetails = authUserDetails;
    }


    public boolean doesUserHavePermissions(ApiUserPermission apiUserPermission) {
        return authUserDetails.getPermission().isHigherOrEqualTo(apiUserPermission);
    }

    public ResourceDetails getResourceDetails() {
        return resourceDetails;
    }

    public AuthUserDetails getAuthUserDetails() {
        return authUserDetails;
    }
}
