package io.ehdev.conrad.service.api.service.project;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.permission.PermissionCreateResponse;
import io.ehdev.conrad.model.permission.PermissionGrant;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
    value = "/api/v1/project/{projectName}/permissions",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectPermissionsEndpoint {

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public ProjectPermissionsEndpoint(PermissionManagementApi permissionManagementApi) {
        this.permissionManagementApi = permissionManagementApi;
    }


    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity deletePermissions(RequestDetails container,
                                            @PathVariable("username") String username) {
        permissionManagementApi.addPermission(username, container.getResourceDetails(), ApiUserPermission.NONE);

        return new ResponseEntity(HttpStatus.OK);
    }

    @ProjectRequired
    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/..", permissions = ApiUserPermission.ADMIN)
    })
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PermissionCreateResponse> addPermission(RequestDetails container,
                                                                  @RequestBody PermissionGrant permissionGrant) {
        boolean created = permissionManagementApi.addPermission(permissionGrant.getUsername(),
            container.getResourceDetails(),
            ApiUserPermission.valueOf(permissionGrant.getPermission().toString()));

        PermissionCreateResponse response = new PermissionCreateResponse(created);
        return new ResponseEntity<>(response, created ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }
}
