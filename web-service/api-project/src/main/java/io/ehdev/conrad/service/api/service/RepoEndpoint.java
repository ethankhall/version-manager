package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestRepoCreateModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
import io.ehdev.conrad.model.rest.commit.RestCommitIdCollection;
import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.util.ConversionUtility.toRestModel;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo")
public class RepoEndpoint {

    private final RepoManagementApi repoManagementApi;

    @Autowired
    public RepoEndpoint(RepoManagementApi repoManagementApi) {
        this.repoManagementApi = repoManagementApi;
    }

    @RequestMapping(value = "/{repoName}", method = RequestMethod.POST)
    public ResponseEntity<RestRepoDetailsModel> createRepo(@PathVariable("projectName") String projectName,
                                                           @PathVariable("repoName") String repoName,
                                                           @RequestBody RestRepoCreateModel createModel,
                                                           @Valid @NotNull ConradUser user) {
        ApiRepoModel qualifiedRepo = new ApiRepoModel(projectName, repoName);
        if (repoManagementApi.doesRepoExist(qualifiedRepo)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        ApiRepoDetailsModel repo = repoManagementApi.createRepo(qualifiedRepo, createModel.getBumperName(), createModel.getRepoUrl());
        return new ResponseEntity<>(toRestModel(repo), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{repoName}/search/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> searchForVersionInHistory(ApiRepoModel repoModel,
                                                                     @RequestBody RestCommitIdCollection versionModel,
                                                                     @Valid @NotNull ConradUser user) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(it -> new ApiCommitModel(it.getCommitId()))
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latest = repoManagementApi.findLatestCommit(repoModel, commits);
        if (!latest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(toRestModel(latest.get()));
        }
    }
}
