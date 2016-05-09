package io.ehdev.conrad.service.api.service.project;

import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.project.CreateProjectRequest;
import io.ehdev.conrad.model.project.GetProjectResponse;
import io.ehdev.conrad.model.project.RepoDefinitionsDetails;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.*;

@Controller
@RequestMapping(
    value = "/api/v1/project/{projectName}",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectEndpoint {

    private final ProjectManagementApi projectManagementApi;

    @Autowired
    public ProjectEndpoint(ProjectManagementApi projectManagementApi) {
        this.projectManagementApi = projectManagementApi;
    }


    @LoggedInUserRequired
    @ProjectRequired(exists = false)
    @InternalLinks()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateProjectRequest> createProject(ApiParameterContainer apiParameterContainer,
                                                              HttpServletRequest request) {
        try {
            projectManagementApi.createProject(apiParameterContainer);

            CreateProjectRequest createProjectModel = new CreateProjectRequest(apiParameterContainer.getProjectName());
            return ResponseEntity.created(URI.create(request.getRequestURL().toString())).body(createProjectModel);
        } catch (ProjectAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ProjectRequired
    @ReadPermissionRequired
    @InternalLinks(links = {
        @InternalLink(name = "tokens", ref = "/token", permissions = ApiUserPermission.ADMIN),
        @InternalLink(name = "permissions", ref = "/permissions", permissions = ApiUserPermission.ADMIN)
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetProjectResponse> getProject(ApiParameterContainer apiParameterContainer) {
        ApiProjectDetails projectDetails = projectManagementApi.getProjectDetails(apiParameterContainer).get();

        List<RepoDefinitionsDetails> details = new ArrayList<>();

        GetProjectResponse projectModel = new GetProjectResponse(projectDetails.getName(), details);

        projectDetails.getDetails().forEach(it -> {
            RepoDefinitionsDetails repo = new RepoDefinitionsDetails(it.getName());
            repo.addLink(toLink(repositorySelfLink(apiParameterContainer, it.getName())));
            details.add(repo);
        });

        return ResponseEntity.ok(projectModel);
    }
}
