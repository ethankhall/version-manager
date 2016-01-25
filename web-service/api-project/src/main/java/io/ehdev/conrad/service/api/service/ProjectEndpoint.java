package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.model.rest.RestProjectModel;
import io.ehdev.conrad.model.user.ConradUser;
import io.ehdev.conrad.service.api.util.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Controller
@RequestMapping("/api/v1/project")
public class ProjectEndpoint {

    private final ProjectManagementApi projectManagementApi;

    @Autowired
    public ProjectEndpoint(ProjectManagementApi projectManagementApi) {
        this.projectManagementApi = projectManagementApi;
    }

    @RequestMapping(value = "/{projectName}", method = RequestMethod.POST)
    public ResponseEntity<RestProjectModel> createProject(@PathVariable("projectName") String projectName,
                                                    HttpServletRequest request,
                                                    @Valid @NotNull ConradUser user) {
        try {
            ApiProjectModel project = projectManagementApi.createProject(projectName);
            RestProjectModel restModel = ConversionUtility.toRestModel(project);
            return ResponseEntity.created(URI.create(request.getRequestURL().toString())).body(restModel);
        } catch (ProjectAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
