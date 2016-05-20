package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.permission.PermissionCreateResponse;
import io.ehdev.conrad.model.permission.PermissionGrant;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.repositoryLink;
import static io.ehdev.conrad.service.api.service.model.LinkUtilities.toLink;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/permissions")
public class RepoPermissionsEndpoint {

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public RepoPermissionsEndpoint(PermissionManagementApi permissionManagementApi) {
        this.permissionManagementApi = permissionManagementApi;
    }

    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../../.."),
        @InternalLink(name = "repository", ref = "/..")
    })
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity deletePermissions(RequestDetails requestDetails,
                                            @PathVariable("username") String username) {
        permissionManagementApi.addPermission(username, requestDetails.getResourceDetails(), ApiUserPermission.NONE);

        return new ResponseEntity(HttpStatus.OK);
    }


    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../../.."),
        @InternalLink(name = "repository", ref = "/..")
    })
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PermissionCreateResponse> addPermission(RequestDetails requestDetails,
                                                                  @RequestBody PermissionGrant permissionGrant) {
        boolean created = permissionManagementApi.addPermission(permissionGrant.getUsername(),
            requestDetails.getResourceDetails(),
            ApiUserPermission.valueOf(permissionGrant.getPermission().toString()));

        PermissionCreateResponse response = new PermissionCreateResponse(created);
        response.addLink(toLink(repositoryLink(requestDetails, "repository")));

        return new ResponseEntity<>(response, created ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }
}
