package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.comparator.ReverseApiCommitComparator;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestVersionCollectionModel;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
public class RepoVersionEndpoint {

    private final RepoManagementApi repoManagementApi;
    private final VersionBumperService versionBumperService;

    @Autowired
    public RepoVersionEndpoint(RepoManagementApi repoManagementApi, VersionBumperService versionBumperService) {
        this.repoManagementApi = repoManagementApi;
        this.versionBumperService = versionBumperService;
    }

    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public ResponseEntity<RestVersionCollectionModel> getAllVersions(ApiRepoModel repoModel,
                                                                     @Valid @NotNull ConradUser user) {
        Stream<RestCommitModel> restCommitStream = repoManagementApi
            .findAllCommits(repoModel)
            .stream()
            .sorted(new ReverseApiCommitComparator())
            .map(ConversionUtility::toRestModel);

        List<RestCommitModel> commits = restCommitStream.collect(Collectors.toList());
        return ResponseEntity.ok(new RestVersionCollectionModel(commits));
    }

    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> createNewVersion(ApiRepoModel repoModel,
                                                            @RequestBody VersionCreateModel versionModel,
                                                            HttpServletRequest request,
                                                            @Valid @NotNull ConradUser user) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitModel::new)
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latestCommit = repoManagementApi.findLatestCommit(repoModel, commits);

        assertHistoryIsNotMissing(commits, latestCommit);

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

    private void assertHistoryIsNotMissing(List<ApiCommitModel> commits, Optional<ApiCommitModel> latestCommit) {
        if(!commits.isEmpty() && !latestCommit.isPresent()) {
            String joinedCommit = commits.stream().map(ApiCommitModel::getCommitId).reduce((t, u) -> t + "," + u).get();
            throw new CommitNotFoundException(joinedCommit);
        }
    }
}
