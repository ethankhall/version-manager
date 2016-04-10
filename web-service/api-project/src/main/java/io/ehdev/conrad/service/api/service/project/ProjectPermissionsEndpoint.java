package io.ehdev.conrad.service.api.service.project;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.permission.PermissionCreateResponse;
import io.ehdev.conrad.model.permission.PermissionGrant;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.projectLink;
import static io.ehdev.conrad.service.api.service.model.LinkUtilities.toLink;

@Controller
@RequestMapping(
    value = "/api/v1/project/{projectName}/permissions",
    consumes = MediaType.APPLICATION_JSON_VALUE,
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
    public ResponseEntity deletePermissions(ApiParameterContainer repoModel,
                                            @PathVariable("username") String username) {
        permissionManagementApi.addPermission(username,
            repoModel.getUser(),
            repoModel.getProjectName(),
            repoModel.getRepoName(),
            ApiUserPermission.NONE);

        return new ResponseEntity(HttpStatus.OK);
    }

    @ProjectRequired
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PermissionCreateResponse> addPermission(ApiParameterContainer repoModel,
                                                                  @RequestBody PermissionGrant permissionGrant) {
        boolean created = permissionManagementApi.addPermission(permissionGrant.getUsername(),
            repoModel.getUser(),
            repoModel.getProjectName(),
            repoModel.getRepoName(),
            ApiUserPermission.valueOf(permissionGrant.getPermission().toString()));

        PermissionCreateResponse response = new PermissionCreateResponse(created);
        response.addLink(toLink(projectLink(repoModel, "project")));

        return new ResponseEntity<>(response, created ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }
}
