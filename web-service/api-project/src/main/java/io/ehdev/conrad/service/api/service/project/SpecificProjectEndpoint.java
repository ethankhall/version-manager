package io.ehdev.conrad.service.api.service.project;

import io.ehdev.conrad.model.permission.PermissionGrant;
import io.ehdev.conrad.model.project.CreateProjectRequest;
import io.ehdev.conrad.model.project.GetProjectResponse;
import io.ehdev.conrad.model.project.RepoDefinitionsDetails;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tech.crom.business.api.PermissionApi;
import tech.crom.business.api.ProjectApi;
import tech.crom.business.api.RepositoryApi;
import tech.crom.database.api.ProjectManager;
import tech.crom.model.project.CromProject;
import tech.crom.model.repository.CromRepo;
import tech.crom.model.security.authorization.CromPermission;
import tech.crom.model.user.CromUser;
import tech.crom.web.api.model.RequestDetails;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(
    value = "/api/v1/project/{projectName}",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class SpecificProjectEndpoint {

    private final ProjectApi projectApi;
    private final RepositoryApi repositoryApi;
    private final PermissionApi permissionApi;

    @Autowired
    public SpecificProjectEndpoint(ProjectApi projectApi, RepositoryApi repositoryApi, PermissionApi permissionApi) {
        this.projectApi = projectApi;
        this.repositoryApi = repositoryApi;
        this.permissionApi = permissionApi;
    }


    @Transactional
    @LoggedInUserRequired
    @ProjectRequired(exists = false)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateProjectRequest> createProject(RequestDetails container,
                                                              HttpServletRequest request) {
        try {
            CromProject project = projectApi.createProject(
                container.getRequestPermission().getCromUser(),
                container.getRawRequest().getProjectName());

            CreateProjectRequest createProjectModel = new CreateProjectRequest(project.getProjectName());
            return ResponseEntity.created(URI.create(request.getRequestURL().toString())).body(createProjectModel);
        } catch (ProjectManager.ProjectAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ProjectRequired
    @ReadPermissionRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetProjectResponse> getProject(RequestDetails container) {
        Collection<CromRepo> repos = repositoryApi.findRepo(container.getCromProject());

        List<RepoDefinitionsDetails> details = new ArrayList<>();

        CromUser user = container.getRequestPermission().getCromUser();
        CromPermission permission = container.getRequestPermission().getProjectPermission();

        ArrayList<PermissionGrant> permissionList = new ArrayList<>();
        if(user != null && permission != null) {
            permissionList.add(new PermissionGrant(
                user.getUserName(),
                PermissionGrant.PermissionDefinition.valueOf(permission.name())));
        }

        GetProjectResponse projectModel = new GetProjectResponse(
            container.getCromProject().getProjectName(),
            details,
            permissionList);

        repos.forEach(it -> details.add(new RepoDefinitionsDetails(it.getRepoName())));

        return ResponseEntity.ok(projectModel);
    }

    @Transactional
    @ProjectRequired
    @AdminPermissionRequired
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(RequestDetails container) {
        Collection<CromRepo> repo = repositoryApi.findRepo(container.getCromProject());
        repo.forEach(repositoryApi::deleteRepo);

        projectApi.deleteProject(container.getCromProject());

        return new ResponseEntity(HttpStatus.OK);
    }
}
