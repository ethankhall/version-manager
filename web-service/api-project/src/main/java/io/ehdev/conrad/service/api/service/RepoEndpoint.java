package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.rest.commit.RestCommitIdCollection;
import io.ehdev.conrad.model.rest.repo.RestUserPermissionModel;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
import io.ehdev.conrad.service.api.service.model.LinkUtilities;
import io.ehdev.conrad.service.api.service.model.repo.CreateRepoRequestModel;
import io.ehdev.conrad.service.api.service.model.repo.CreateRepoResponseModel;
import io.ehdev.conrad.service.api.service.model.repo.GetRepoModel;
import io.ehdev.conrad.service.api.service.model.version.VersionSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
public class RepoEndpoint {

    private final RepoManagementApi repoManagementApi;
    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public RepoEndpoint(RepoManagementApi repoManagementApi,
                        PermissionManagementApi permissionManagementApi) {
        this.repoManagementApi = repoManagementApi;
        this.permissionManagementApi = permissionManagementApi;
    }

    @LoggedInUserRequired
    @WritePermissionRequired
    @RepoRequired(exists = false)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreateRepoResponseModel> createRepo(ApiParameterContainer apiParameterContainer,
                                                              @RequestBody CreateRepoRequestModel createModel) {
        DefaultApiRepoModel newModel = new DefaultApiRepoModel(apiParameterContainer.getProjectName(), apiParameterContainer.getRepoName(), createModel.getRepoUrl());
        ApiRepoDetailsModel repo = repoManagementApi.createRepo(newModel, createModel.getBumperName());

        CreateRepoResponseModel model = new CreateRepoResponseModel();
        model.setUrl(repo.getRepo().getUrl());
        model.setRepoName(repo.getRepo().getRepoName());
        model.setProjectName(repo.getRepo().getProjectName());
        model.add(LinkUtilities.repositorySelfLink(apiParameterContainer));

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<GetRepoModel> getRepoDetails(ApiParameterContainer container) {
        ApiRepoDetailsModel details = repoManagementApi.getDetails(container).get();

        GetRepoModel restRepoModel = new GetRepoModel();
        restRepoModel.setProjectName(details.getRepo().getProjectName());
        restRepoModel.setRepoName(details.getRepo().getProjectName());
        restRepoModel.setUrl(details.getRepo().getUrl());

        permissionManagementApi.getPermissionsForProject(container).forEach(it -> {
            restRepoModel.addPermission(new RestUserPermissionModel(it.getUsername(), it.getPermissions()));
        });

        restRepoModel.add(LinkUtilities.repositorySelfLink(container));
        restRepoModel.add(LinkUtilities.versionListLink(container, LinkUtilities.VERSION_REF));
        return ResponseEntity.ok(restRepoModel);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/search/version", method = RequestMethod.POST)
    public ResponseEntity<VersionSearchResponse> searchForVersionInHistory(ApiParameterContainer apiParameterContainer,
                                                                           @RequestBody RestCommitIdCollection versionModel) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(it -> new ApiCommitModel(it.getCommitId()))
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latest = repoManagementApi.findLatestCommit(apiParameterContainer, commits);
        if (!latest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ApiCommitModel commit = latest.get();
            VersionSearchResponse body = new VersionSearchResponse(commit.getCommitId(), commit.getVersion());
            body.add(LinkUtilities.versionSelfLink(apiParameterContainer, commit.getVersion()));
            return ResponseEntity.ok(body);
        }
    }
}
