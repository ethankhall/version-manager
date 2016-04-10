package io.ehdev.conrad.service.api.service.user;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;
import io.ehdev.conrad.model.user.GetUserAccessResponse;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;


@Controller
@RequestMapping("/api/v1/user")
public class UserEndpoint {

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    UserEndpoint(PermissionManagementApi permissionManagementApi) {
        this.permissionManagementApi = permissionManagementApi;
    }

    @LoggedInUserRequired
    @RequestMapping(value = "/access")
    public ResponseEntity<GetUserAccessResponse> findAccess(ApiParameterContainer apiParameterContainer) {
        UserPermissionGrants userPermissions = permissionManagementApi.getUserPermissions(apiParameterContainer.getUser());

        //@formatter:off
        GetUserAccessResponse response = new GetUserAccessResponse(
            userPermissions.getProjectPermissions()
                .stream()
                .map(it -> new GetUserAccessResponse.ProjectPermissionDetails(it.getProjectName(), it.getPermission().toString()))
                .collect(Collectors.toList()),
            userPermissions.getRepoPermissions()
                .stream()
                .map(it -> new GetUserAccessResponse.RepoPermissionDetails(it.getProjectName(), it.getRepoName(), it.getPermission().toString()))
                .collect(Collectors.toList())
        );
        //@formatter:on
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @LoggedInUserRequired
    @RequestMapping(value = "/profile")
    public ResponseEntity findProfile() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
