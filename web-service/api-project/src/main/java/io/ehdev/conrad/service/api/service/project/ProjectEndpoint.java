package io.ehdev.conrad.service.api.service.project;

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
import tech.crom.business.api.ProjectApi;
import tech.crom.business.api.RepositoryApi;
import tech.crom.database.api.ProjectManager;
import tech.crom.model.project.CromProject;
import tech.crom.model.repository.CromRepo;
import tech.crom.model.security.authorization.CromPermission;
import tech.crom.web.api.model.RequestDetails;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.repositorySelfLink;
import static io.ehdev.conrad.service.api.service.model.LinkUtilities.toLink;

@Controller
@RequestMapping(
    value = "/api/v1/project/{projectName}",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectEndpoint {

    private final ProjectApi projectManager;
    private final RepositoryApi repositoryApi;

    @Autowired
    public ProjectEndpoint(ProjectApi projectManager, RepositoryApi repositoryApi) {
        this.projectManager = projectManager;
        this.repositoryApi = repositoryApi;
    }


    @LoggedInUserRequired
    @ProjectRequired(exists = false)
    @InternalLinks()
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateProjectRequest> createProject(RequestDetails container,
                                                              HttpServletRequest request) {
        try {
            CromProject project = projectManager.createProject(container.getRawRequest().getProjectName());

            CreateProjectRequest createProjectModel = new CreateProjectRequest(project.getProjectName());
            return ResponseEntity.created(URI.create(request.getRequestURL().toString())).body(createProjectModel);
        } catch (ProjectManager.ProjectAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ProjectRequired
    @ReadPermissionRequired
    @InternalLinks(links = {
        @InternalLink(name = "tokens", ref = "/token", permissions = CromPermission.ADMIN),
        @InternalLink(name = "permissions", ref = "/permissions", permissions = CromPermission.ADMIN)
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetProjectResponse> getProject(RequestDetails container) {
        Collection<CromRepo> repos = repositoryApi.findRepo(container.getCromProject());

        List<RepoDefinitionsDetails> details = new ArrayList<>();

        GetProjectResponse projectModel = new GetProjectResponse(container.getCromProject().getProjectName(), details);

        repos.forEach(it -> {
            RepoDefinitionsDetails repo = new RepoDefinitionsDetails(it.getRepoName());
            repo.addLink(toLink(repositorySelfLink(container, it.getRepoName())));
            details.add(repo);
        });

        return ResponseEntity.ok(projectModel);
    }
}
