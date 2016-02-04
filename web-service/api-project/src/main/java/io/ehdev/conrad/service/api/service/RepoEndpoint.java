package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestRepoCreateModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
import io.ehdev.conrad.model.rest.RestVersionCollectionModel;
import io.ehdev.conrad.model.rest.commit.RestCommitIdCollection;
import io.ehdev.conrad.model.user.ConradUser;
import io.ehdev.conrad.model.version.VersionCreateModel;
import io.ehdev.conrad.service.api.util.ConversionUtility;
import io.ehdev.conrad.version.bumper.api.VersionBumperService;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.VersionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.ehdev.conrad.service.api.util.ConversionUtility.toRestModel;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo")
public class RepoEndpoint {

    private final RepoManagementApi repoManagementApi;
    private final VersionBumperService versionBumperService;

    @Autowired
    public RepoEndpoint(RepoManagementApi repoManagementApi,
                        VersionBumperService versionBumperService) {
        this.repoManagementApi = repoManagementApi;
        this.versionBumperService = versionBumperService;
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

    @RequestMapping(value = "/{repoName}/details", method = RequestMethod.GET)
    public ResponseEntity<RestRepoDetailsModel> getRepoDetails(ApiRepoModel repoModel,
                                                               @Valid @NotNull ConradUser user) {
        return ResponseEntity.ok(toRestModel(repoManagementApi.getDetails(repoModel).get()));
    }

    @RequestMapping(value = "/{repoName}/versions", method = RequestMethod.GET)
    public ResponseEntity<RestVersionCollectionModel> getAllVersions(ApiRepoModel repoModel,
                                                                     @Valid @NotNull ConradUser user) {
        Stream<RestCommitModel> restCommitStream = repoManagementApi
            .findAllCommits(repoModel)
            .stream()
            .map(ConversionUtility::toRestModel);

        List<RestCommitModel> commits = restCommitStream.collect(Collectors.toList());
        return ResponseEntity.ok(new RestVersionCollectionModel(commits));
    }

    @RequestMapping(value = "/{repoName}/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> createNewVersion(ApiRepoModel repoModel,
                                                            @RequestBody VersionCreateModel versionModel,
                                                            HttpServletRequest request,
                                                            @Valid @NotNull ConradUser user) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitModel::new)
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latestCommit = repoManagementApi.findLatestCommit(repoModel, commits);

        if(!commits.isEmpty() && !latestCommit.isPresent()) {
            String joinedCommit = commits.stream().map(ApiCommitModel::getCommitId).reduce((t, u) -> t + "," + u).get();
            throw new CommitNotFoundException(joinedCommit);
        }

        CommitVersion nextVersion = versionBumperService.findNextVersion(
            repoModel,
            versionModel.getCommitId(),
            versionModel.getMessage(),
            VersionFactory.parseApiModel(latestCommit.orElse(null)));

        ApiCommitModel nextCommit = new ApiCommitModel(versionModel.getCommitId(), nextVersion.toVersionString());
        repoManagementApi.createCommit(repoModel, nextCommit, latestCommit.orElse(null));

        URI uri = URI.create(request.getRequestURL().toString() + "/" + nextCommit.getVersion());
        return ResponseEntity.created(uri).body(toRestModel(nextCommit));
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

    @RequestMapping(value = "/{repoName}/version/{versionArg}", method = RequestMethod.GET)
    public ResponseEntity<RestCommitModel> findVersion(ApiRepoModel repoModel,
                                                       @RequestParam("versionArg") String versionArg,
                                                       @Valid @NotNull ConradUser user) {
        Optional<ApiCommitModel> commit = repoManagementApi.findCommit(repoModel, versionArg);
        if (commit.isPresent()) {
            return ResponseEntity.ok(toRestModel(commit.get()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
