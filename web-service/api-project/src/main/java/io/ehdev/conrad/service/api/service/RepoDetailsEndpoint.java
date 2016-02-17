package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.rest.repo.RestRepoModel;
import io.ehdev.conrad.model.rest.repo.RestUserPermissionModel;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
public class RepoDetailsEndpoint {
    private final RepoManagementApi repoManagementApi;

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public RepoDetailsEndpoint(RepoManagementApi repoManagementApi,
                               PermissionManagementApi permissionManagementApi) {
        this.repoManagementApi = repoManagementApi;
        this.permissionManagementApi = permissionManagementApi;
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseEntity<RestRepoModel> getRepoDetails(ApiParameterContainer repoModel) {
        ApiRepoDetailsModel details = repoManagementApi.getDetails(repoModel).get();

        RestRepoModel restRepoModel = new RestRepoModel();
        restRepoModel.setProjectName(details.getRepo().getProjectName());
        restRepoModel.setRepoName(details.getRepo().getProjectName());
        restRepoModel.setUrl(details.getRepo().getUrl());

        permissionManagementApi.getPermissionsForProject(repoModel).forEach(it -> {
            restRepoModel.addPermission(new RestUserPermissionModel(it.getUsername(), it.getPermissions()));
        });

        return ResponseEntity.ok(restRepoModel);
    }

    @LoggedInUserRequired
    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/details/permissions/{username}", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/details/permissions/{username}/{permissionType}", method = RequestMethod.POST)
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
