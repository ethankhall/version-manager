package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.permission.PermissionCreateResponse;
import io.ehdev.conrad.model.permission.PermissionGrant;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses({
        @ApiResponse(code = 200, message = "Permissions deleted", response = PermissionCreateResponse.class),
        @ApiResponse(code = 401, message = "You do not have permissions to add permissions"),
        @ApiResponse(code = 403, message = "Unable to create permission", response = PermissionCreateResponse.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectName", value = "The project name", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "repoName", value = "The repo name", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "username", value = "Username to add to the project", required = true, dataType = "string", paramType = "path"),
    })
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
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

    @ApiResponses({
        @ApiResponse(code = 201, message = "New permission created", response = PermissionCreateResponse.class),
        @ApiResponse(code = 401, message = "You do not have permissions to add permissions"),
        @ApiResponse(code = 403, message = "Unable to create permission", response = PermissionCreateResponse.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectName", value = "The project name", required = true, dataType = "string", paramType = "path"),
        @ApiImplicitParam(name = "repoName", value = "The repo name", required = true, dataType = "string", paramType = "path"),
    })
    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PermissionCreateResponse> addPermission(ApiParameterContainer repoModel,
                                                                  @RequestBody PermissionGrant permissionGrant) {
        boolean created = permissionManagementApi.addPermission(permissionGrant.getUsername(),
            repoModel.getUser(),
            repoModel.getProjectName(),
            repoModel.getRepoName(),
            ApiUserPermission.valueOf(permissionGrant.getPermission().toString()));

        PermissionCreateResponse response = new PermissionCreateResponse(created);
        response.addLink(toLink(repositoryLink(repoModel, "repository")));

        return new ResponseEntity<>(response, created ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }
}
