package io.ehdev.conrad.service.api.service.repo;

import tech.crom.rest.model.commit.CommitIdCollection;
import tech.crom.rest.model.permission.PermissionGrant;
import tech.crom.rest.model.repository.CreateRepoRequest;
import tech.crom.rest.model.repository.CreateRepoResponse;
import tech.crom.rest.model.repository.GetRepoResponse;
import tech.crom.rest.model.version.VersionSearchResponse;
import io.ehdev.conrad.service.api.aop.annotation.AdminPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
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
import tech.crom.model.repository.CromRepoDetails;
import tech.crom.web.api.model.RequestDetails;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @RepoRequired
    @Transactional
    @AdminPermissionRequired
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteRepo(RequestDetails requestDetails) {
        repositoryApi.deleteRepo(requestDetails.getCromRepo());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
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
                .map(it -> new RequestedCommit(it.getCommitId(), "[set version " + it.getVersion() + "]", it.getCreatedAt()))
                .collect(Collectors.toList());

            CommitIdContainer commitIdContainer = null;
            for (RequestedCommit requestedCommit : requestedCommits) {
                List<CommitIdContainer> commitList = commitIdContainer == null ? Collections.emptyList() : Collections.singletonList(commitIdContainer);
                PersistedCommit commit = commitApi.createCommit(repo, requestedCommit, commitList);
                commitIdContainer = new CommitIdContainer(commit.getCommitId());
            }
        }

        CreateRepoResponse model = new CreateRepoResponse(
            requestDetails.getCromProject().getProjectName(),
            repo.getRepoName(),
            createModel.getRepoUrl());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @RepoRequired
    @ReadPermissionRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetRepoResponse> getRepoDetails(RequestDetails requestDetails) {
        CromRepoDetails repoDetails = repositoryApi.getRepoDetails(requestDetails.getCromRepo());

        GetRepoResponse restRepoModel = new GetRepoResponse(
            requestDetails.getCromProject().getProjectName(),
            requestDetails.getCromRepo().getRepoName(),
            repoDetails.getCheckoutUrl()
        );

        permissionApi.getPermissions(repoDetails.getCromRepo()).forEach(it -> {
            restRepoModel.addPermission(
                new PermissionGrant(it.getCromUser().getUserName(),
                    PermissionGrant.AccessLevel.valueOf(it.getCromPermission().name())));
        });

        return ResponseEntity.ok(restRepoModel);
    }

    @RepoRequired
    @ReadPermissionRequired
    @RequestMapping(value = "/search/version", method = RequestMethod.POST)
    public ResponseEntity<VersionSearchResponse> searchForVersionInHistory(RequestDetails requestDetails,
                                                                           @RequestBody CommitIdCollection versionModel) {

        List<CommitIdContainer> commits = versionModel.getCommits().stream().map(CommitIdContainer::new).collect(Collectors.toList());
        PersistedCommit latestCommit = commitApi.findLatestCommit(requestDetails.getCromRepo(), commits);

        if (latestCommit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            VersionSearchResponse body = new VersionSearchResponse(latestCommit.getCommitId(),
                latestCommit.getVersion().getVersionString(),
                latestCommit.getCreatedAt());
            return ResponseEntity.ok(body);
        }
    }
}