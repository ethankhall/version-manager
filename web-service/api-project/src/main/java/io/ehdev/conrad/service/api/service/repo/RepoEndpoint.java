package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.repo.RepoCreateModel;
import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.commit.CommitIdCollection;
import io.ehdev.conrad.model.permission.PermissionGrant;
import io.ehdev.conrad.model.repository.CreateRepoRequest;
import io.ehdev.conrad.model.repository.CreateRepoResponse;
import io.ehdev.conrad.model.repository.GetRepoResponse;
import io.ehdev.conrad.model.version.VersionSearchResponse;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.*;

@Service
@RequestMapping(
    value = "/api/v1/project/{projectName}/repo/{repoName}",
    produces = MediaType.APPLICATION_JSON_VALUE)

public class RepoEndpoint {

    private final RepoManagementApi repoManagementApi;
    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public RepoEndpoint(RepoManagementApi repoManagementApi,
                        PermissionManagementApi permissionManagementApi) {
        this.repoManagementApi = repoManagementApi;
        this.permissionManagementApi = permissionManagementApi;
    }

    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteRepo(ResourceDetails apiParameterContainer) {
        repoManagementApi.delete(apiParameterContainer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @InternalLinks()
    @WritePermissionRequired
    @RepoRequired(exists = false)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateRepoResponse> createRepo(ResourceDetails apiParameterContainer,
                                                         @RequestBody CreateRepoRequest createModel) {
        RepoCreateModel repoCreateModel = new RepoCreateModel(apiParameterContainer,
            createModel.getBumperName(),
            "",
            createModel.getRepoUrl(),
            true);

        ApiRepoDetailsModel repo = repoManagementApi.createRepo(repoCreateModel);

        if (createModel.getHistory() != null) {
            ApiCommitModel prev = null;
            for (CreateRepoRequest.CreateHistory it : createModel.getHistory()) {
                ApiCommitModel nextVersion = new ApiCommitModel(it.getCommitId(), it.getVersion(), null);
                repoManagementApi.createCommit(apiParameterContainer, nextVersion, prev);
                prev = nextVersion;
            }
        }

        CreateRepoResponse model = new CreateRepoResponse(
            repo.getRepo().getProjectName(),
            repo.getRepo().getRepoName(),
            repo.getRepo().getUrl());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../.."),
        @InternalLink(name = "tokens", ref = "/token", permissions = ApiUserPermission.ADMIN),
        @InternalLink(name = "permissions", ref = "/permissions", permissions = ApiUserPermission.ADMIN),
        @InternalLink(name = VERSION_REF, ref = "/versions"),
        @InternalLink(name = "latest", ref = "/version/latest")
    })
    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetRepoResponse> getRepoDetails(RequestDetails requestDetails) {
        ApiRepoDetailsModel details = repoManagementApi.getDetails(requestDetails.getResourceDetails()).get();

        GetRepoResponse restRepoModel = new GetRepoResponse(
            details.getRepo().getProjectName(),
            details.getRepo().getRepoName(),
            details.getRepo().getUrl()
        );

        permissionManagementApi.getPermissions(requestDetails.getResourceDetails()).forEach(it -> restRepoModel.addPermission(
            new PermissionGrant(it.getUsername(), PermissionGrant.PermissionDefinition.valueOf(it.getPermissions()))));

        return ResponseEntity.ok(restRepoModel);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/search/version", method = RequestMethod.POST)
    public ResponseEntity<VersionSearchResponse> searchForVersionInHistory(RequestDetails requestDetails,
                                                                           @RequestBody CommitIdCollection versionModel) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitModel::new)
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latest = repoManagementApi.findLatestCommit(requestDetails.getResourceDetails(), commits);
        if (!latest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ApiCommitModel commit = latest.get();
            VersionSearchResponse body = new VersionSearchResponse(commit.getCommitId(), commit.getVersion(), commit.getCreatedAt());
            body.addLink(toLink(versionSelfLink(requestDetails, commit.getVersion())));
            return ResponseEntity.ok(body);
        }
    }
}
