package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.service.model.LinkUtilities;
import io.ehdev.conrad.service.api.service.model.project.CreateProjectModel;
import io.ehdev.conrad.service.api.service.model.project.GetProjectModel;
import io.ehdev.conrad.service.api.service.model.project.RepoDefinitionsDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/api/v1/project/{projectName}")
public class ProjectEndpoint {

    private final ProjectManagementApi projectManagementApi;

    @Autowired
    public ProjectEndpoint(ProjectManagementApi projectManagementApi) {
        this.projectManagementApi = projectManagementApi;
    }

    @LoggedInUserRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateProjectModel> createProject(ApiParameterContainer apiParameterContainer,
                                                            HttpServletRequest request) {
        try {
            projectManagementApi.createProject(apiParameterContainer);

            CreateProjectModel createProjectModel = new CreateProjectModel();
            createProjectModel.setName(apiParameterContainer.getProjectName());
            createProjectModel.add(projectSelfLink(apiParameterContainer));

            return ResponseEntity.created(URI.create(request.getRequestURL().toString())).body(createProjectModel);
        } catch (ProjectAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @LoggedInUserRequired
    @ReadPermissionRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetProjectModel> getProject(ApiParameterContainer apiParameterContainer) {
        ApiProjectDetails projectDetails = projectManagementApi.getProjectDetails(apiParameterContainer);
        GetProjectModel projectModel = new GetProjectModel();
        projectModel.setName(projectDetails.getName());
        projectModel.add(projectSelfLink(apiParameterContainer));

        projectDetails.getDetails().forEach(it -> {
            RepoDefinitionsDetails repo = new RepoDefinitionsDetails();
            repo.setName(it.getName());

            repo.add(LinkUtilities.repositorySelfLink(apiParameterContainer, it.getName()));
            projectModel.addRepo(repo);
        });

        return ResponseEntity.ok(projectModel);
    }

    public static Link projectSelfLink(ApiParameterContainer apiParameterContainer) {
        return linkTo(methodOn(ProjectEndpoint.class, apiParameterContainer.getProjectName()).getProject(apiParameterContainer)).withSelfRel();
    }
}
