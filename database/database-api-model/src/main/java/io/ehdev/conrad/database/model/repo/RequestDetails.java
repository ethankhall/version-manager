package io.ehdev.conrad.database.model.repo;

import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
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
