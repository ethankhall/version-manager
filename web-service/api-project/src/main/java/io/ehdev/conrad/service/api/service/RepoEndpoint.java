package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestRepoCreateModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
import io.ehdev.conrad.model.rest.commit.RestCommitIdCollection;
import io.ehdev.conrad.model.rest.repo.RestRepoModel;
import io.ehdev.conrad.model.rest.repo.RestUserPermissionModel;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
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

import static io.ehdev.conrad.service.api.util.ConversionUtility.toRestModel;

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
    public ResponseEntity<RestRepoDetailsModel> createRepo(ApiParameterContainer apiParameterContainer,
                                                           @RequestBody RestRepoCreateModel createModel) {
        DefaultApiRepoModel newModel = new DefaultApiRepoModel(apiParameterContainer.getProjectName(), apiParameterContainer.getRepoName(), createModel.getRepoUrl());
        ApiRepoDetailsModel repo = repoManagementApi.createRepo(newModel, createModel.getBumperName());
        return new ResponseEntity<>(toRestModel(repo), HttpStatus.CREATED);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestRepoModel> getRepoDetails(ApiParameterContainer repoModel) {
        ApiRepoDetailsModel details = repoManagementApi.getDetails(repoModel).get();

        RestRepoModel restRepoModel = new RestRepoModel();
        restRepoModel.setProjectName(details.getRepo().getProjectName());
        restRepoModel.setRepoName(details.getRepo().getProjectName());
        restRepoModel.setUrl(details.getRepo().getUrl());

        permissionManagementApi.getPermissionsForProject(repoModel).forEach(it -> {
            restRepoModel.addPermission(new RestUserPermissionModel(it.getUsername(), it.getPermissions()));
        });

        return ResponseEntity.ok(restRepoModel);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/search/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> searchForVersionInHistory(ApiParameterContainer apiParameterContainer,
                                                                     @RequestBody RestCommitIdCollection versionModel) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(it -> new ApiCommitModel(it.getCommitId()))
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latest = repoManagementApi.findLatestCommit(apiParameterContainer, commits);
        if (!latest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(toRestModel(latest.get()));
        }
    }
}
