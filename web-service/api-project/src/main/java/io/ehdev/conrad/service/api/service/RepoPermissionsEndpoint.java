package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}/permissions")
public class RepoPermissionsEndpoint {

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public RepoPermissionsEndpoint(PermissionManagementApi permissionManagementApi) {
        this.permissionManagementApi = permissionManagementApi;
    }

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

    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/{username}/{permissionType}", method = RequestMethod.POST)
    public ResponseEntity addPermission(ApiParameterContainer repoModel,
                                        @PathVariable("username") String username,
                                        @PathVariable("permissionType") String permissionType) {
        permissionManagementApi.addPermission(username,
            repoModel.getUser(),
            repoModel.getProjectName(),
            repoModel.getRepoName(),
            ApiUserPermission.valueOf(permissionType.toUpperCase()));

        return new ResponseEntity(HttpStatus.OK);
    }
}
