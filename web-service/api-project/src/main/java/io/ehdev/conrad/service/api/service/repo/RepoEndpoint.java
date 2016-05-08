package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
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
import io.ehdev.conrad.service.api.service.annotation.JsonPermissionView;
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
    public ResponseEntity deleteRepo(ApiParameterContainer apiParameterContainer) {
        repoManagementApi.delete(apiParameterContainer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @InternalLinks()
    @WritePermissionRequired
    @RepoRequired(exists = false)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateRepoResponse> createRepo(ApiParameterContainer apiParameterContainer,
                                                         @RequestBody CreateRepoRequest createModel) {
        DefaultApiRepoModel newModel = new DefaultApiRepoModel(
            apiParameterContainer.getProjectName(),
            apiParameterContainer.getRepoName(),
            createModel.getRepoUrl());

        ApiRepoDetailsModel repo = repoManagementApi.createRepo(newModel, createModel.getBumperName(), true);

        if (createModel.getHistory() != null) {
            ApiCommitModel prev = null;
            for (CreateRepoRequest.CreateHistory it : createModel.getHistory()) {
                ApiCommitModel nextVersion = new ApiCommitModel(it.getCommitId(), it.getVersion());
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

    @JsonPermissionView
    @InternalLinks(links = {
        @InternalLink(name = "tokens", ref = "/token", permissions = ApiUserPermission.ADMIN),
        @InternalLink(name = VERSION_REF, ref = "/versions"),
        @InternalLink(name = "latest", ref = "/version/latest")
    })
    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetRepoResponse> getRepoDetails(ApiParameterContainer container) {
        ApiRepoDetailsModel details = repoManagementApi.getDetails(container).get();

        GetRepoResponse restRepoModel = new GetRepoResponse(
            details.getRepo().getProjectName(),
            details.getRepo().getRepoName(),
            details.getRepo().getUrl()
        );

        permissionManagementApi.getPermissions(container).forEach(it -> {
            restRepoModel.addPermission(
                new PermissionGrant(it.getUsername(), PermissionGrant.PermissionDefinition.valueOf(it.getPermissions())));
        });

        return ResponseEntity.ok(restRepoModel);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/search/version", method = RequestMethod.POST)
    public ResponseEntity<VersionSearchResponse> searchForVersionInHistory(ApiParameterContainer apiParameterContainer,
                                                                           @RequestBody CommitIdCollection versionModel) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitModel::new)
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latest = repoManagementApi.findLatestCommit(apiParameterContainer, commits);
        if (!latest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ApiCommitModel commit = latest.get();
            VersionSearchResponse body = new VersionSearchResponse(commit.getCommitId(), commit.getVersion());
            body.addLink(toLink(versionSelfLink(apiParameterContainer, commit.getVersion())));
            return ResponseEntity.ok(body);
        }
    }
}
