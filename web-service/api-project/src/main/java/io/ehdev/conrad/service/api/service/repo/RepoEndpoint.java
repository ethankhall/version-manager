package io.ehdev.conrad.service.api.service.repo;

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
import tech.crom.business.api.CommitApi;
import tech.crom.business.api.PermissionApi;
import tech.crom.business.api.RepositoryApi;
import tech.crom.business.api.VersionBumperApi;
import tech.crom.model.bumper.CromVersionBumper;
import tech.crom.model.commit.CommitIdContainer;
import tech.crom.model.commit.impl.PersistedCommit;
import tech.crom.model.commit.impl.RequestedCommit;
import tech.crom.model.repository.CromRepo;
import tech.crom.model.security.authorization.CromPermission;
import tech.crom.web.api.model.RequestDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.*;

@Service
@RequestMapping(
    value = "/api/v1/project/{projectName}/repo/{repoName}",
    produces = MediaType.APPLICATION_JSON_VALUE)

public class RepoEndpoint {

    private final RepositoryApi repositoryApi;
    private final PermissionApi permissionApi;
    private final VersionBumperApi versionBumperApi;
    private final CommitApi commitApi;

    @Autowired
    public RepoEndpoint(RepositoryApi repositoryApi,
                        PermissionApi permissionApi,
                        VersionBumperApi versionBumperApi,
                        CommitApi commitApi) {
        this.repositoryApi = repositoryApi;
        this.permissionApi = permissionApi;
        this.versionBumperApi = versionBumperApi;
        this.commitApi = commitApi;
    }

    @AdminPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteRepo(RequestDetails requestDetails) {
        repositoryApi.deleteRepo(requestDetails.getCromRepo());
        return new ResponseEntity(HttpStatus.OK);
    }

    @InternalLinks()
    @WritePermissionRequired
    @RepoRequired(exists = false)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateRepoResponse> createRepo(RequestDetails requestDetails,
                                                         @RequestBody CreateRepoRequest createModel) {
        String repoName = requestDetails.getRawRequest().getRepoName();
        CromVersionBumper versionBumper = versionBumperApi.findVersionBumper(createModel.getBumperName());
        CromRepo repo = repositoryApi.createRepo(requestDetails.getCromProject(),
            repoName,
            versionBumper,
            createModel.getRepoUrl(),
            createModel.getDescription(),
            true);

        if (createModel.getHistory() != null) {
            List<RequestedCommit> requestedCommits = createModel
                .getHistory()
                .stream()
                .map(it -> new RequestedCommit(it.getCommitId(), "[set version " + it.getVersion() + "]", null))
                .collect(Collectors.toList());

            CommitIdContainer commitIdContainer = null;
            for(RequestedCommit requestedCommit: requestedCommits) {
                PersistedCommit commit = commitApi.createCommit(repo, requestedCommit, Collections.singletonList(commitIdContainer));
                commitIdContainer = new CommitIdContainer(commit.getCommitId());
            }
        }

        CreateRepoResponse model = new CreateRepoResponse(
            requestDetails.getCromProject().getProjectName(),
            repo.getRepoName(),
            createModel.getRepoUrl());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../.."),
        @InternalLink(name = "tokens", ref = "/token", permissions = CromPermission.ADMIN),
        @InternalLink(name = "permissions", ref = "/permissions", permissions = CromPermission.ADMIN),
        @InternalLink(name = VERSION_REF, ref = "/versions"),
        @InternalLink(name = "latest", ref = "/version/latest")
    })
    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetRepoResponse> getRepoDetails(RequestDetails requestDetails) {
        ApiRepoDetailsModel details = repositoryApi.getDetails(requestDetails.getResourceDetails()).get();

        GetRepoResponse restRepoModel = new GetRepoResponse(
            details.getRepo().getProjectName(),
            details.getRepo().getRepoName(),
            details.getRepo().getUrl()
        );

        permissionApi.getPermissions(requestDetails.getResourceDetails()).forEach(it -> restRepoModel.addPermission(
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

        Optional<ApiCommitModel> latest = repositoryApi.findLatestCommit(requestDetails.getResourceDetails(), commits);
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
