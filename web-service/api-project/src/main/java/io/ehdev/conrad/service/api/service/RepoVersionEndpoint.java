package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.comparator.ReverseApiCommitComparator;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestVersionCollectionModel;
import io.ehdev.conrad.model.version.VersionCreateModel;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
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

    @ReadPermissionRequired
    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public ResponseEntity<RestVersionCollectionModel> getAllVersions(ApiParameterContainer apiParameterContainer) {
        Stream<RestCommitModel> restCommitStream = repoManagementApi
            .findAllCommits(apiParameterContainer)
            .stream()
            .sorted(new ReverseApiCommitComparator())
            .map(ConversionUtility::toRestModel);

        List<RestCommitModel> commits = restCommitStream.collect(Collectors.toList());
        return ResponseEntity.ok(new RestVersionCollectionModel(commits));
    }

    @WritePermissionRequired
    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> createNewVersion(ApiParameterContainer apiParameterContainer,
                                                            @RequestBody VersionCreateModel versionModel,
                                                            HttpServletRequest request) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitModel::new)
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latestCommit = repoManagementApi.findLatestCommit(apiParameterContainer, commits);

        assertHistoryIsNotMissing(commits, latestCommit);

        CommitVersion nextVersion = versionBumperService.findNextVersion(
            apiParameterContainer,
            versionModel.getCommitId(),
            versionModel.getMessage(),
            VersionFactory.parseApiModel(latestCommit.orElse(null)));

        ApiCommitModel nextCommit = new ApiCommitModel(versionModel.getCommitId(), nextVersion.toVersionString());
        repoManagementApi.createCommit(apiParameterContainer, nextCommit, latestCommit.orElse(null));

        URI uri = URI.create(request.getRequestURL().toString() + "/" + nextCommit.getVersion());
        return ResponseEntity.created(uri).body(toRestModel(nextCommit));
    }

    @ReadPermissionRequired
    @RequestMapping(value = "/{repoName}/version/{versionArg}", method = RequestMethod.GET)
    public ResponseEntity<RestCommitModel> findVersion(ApiParameterContainer apiParameterContainer,
                                                       @RequestParam("versionArg") String versionArg) {
        Optional<ApiCommitModel> commit = repoManagementApi.findCommit(apiParameterContainer, versionArg);
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
