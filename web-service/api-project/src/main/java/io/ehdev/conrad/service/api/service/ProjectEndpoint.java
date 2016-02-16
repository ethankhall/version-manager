package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.rest.RestProjectModel;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.util.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@RequestMapping("/api/v1/project")
public class ProjectEndpoint {

    private final ProjectManagementApi projectManagementApi;

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public ProjectEndpoint(ProjectManagementApi projectManagementApi, PermissionManagementApi permissionManagementApi) {
        this.projectManagementApi = projectManagementApi;
        this.permissionManagementApi = permissionManagementApi;
    }

    @LoggedInUserRequired
    @RequestMapping(value = "/{projectName}", method = RequestMethod.POST)
    public ResponseEntity<RestProjectModel> createProject(ApiParameterContainer apiParameterContainer,
                                                          HttpServletRequest request) {
        try {
            ApiProjectModel project = projectManagementApi.createProject(apiParameterContainer.getProjectName());
            String userName = apiParameterContainer.getUser().getUserName();
            permissionManagementApi.forceAddPermission(userName, project.getName(), null, ApiUserPermission.ADMIN);
            RestProjectModel restModel = ConversionUtility.toRestModel(project);
            return ResponseEntity.created(URI.create(request.getRequestURL().toString())).body(restModel);
        } catch (ProjectAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
