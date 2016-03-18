package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.comparator.ReverseApiCommitComparator;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.version.CreateVersionRequest;
import io.ehdev.conrad.model.version.CreateVersionResponse;
import io.ehdev.conrad.model.version.GetAllVersionsResponse;
import io.ehdev.conrad.model.version.GetVersionResponse;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
import io.ehdev.conrad.version.bumper.api.VersionBumperService;
import io.ehdev.conrad.version.commit.CommitVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.*;

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
    @RepoRequired(exists = true)
    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public ResponseEntity<GetAllVersionsResponse> getAllVersions(ApiParameterContainer apiParameterContainer) {
        GetAllVersionsResponse response = new GetAllVersionsResponse();

        repoManagementApi
            .findAllCommits(apiParameterContainer)
            .stream()
            .sorted(new ReverseApiCommitComparator())
            .forEach(it -> {
                GetAllVersionsResponse.CommitModel commit = new GetAllVersionsResponse.CommitModel(it.getCommitId(), it.getVersion());
                commit.addLink(toLink(versionSelfLink(apiParameterContainer, it.getVersion())));
                response.addCommit(commit);
            });

        if(!response.getCommits().isEmpty()) {
            response.setLatest(response.getCommits().get(0));
        }

        response.addLink(toLink(repositoryLink(apiParameterContainer, REPOSITORY_REF)));
        response.addLink(toLink(versionListLink(apiParameterContainer, "self")));

        return ResponseEntity.ok(response);
    }

    @WritePermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public ResponseEntity<CreateVersionResponse> createNewVersion(ApiParameterContainer apiParameterContainer,
                                                                  @RequestBody CreateVersionRequest versionModel,
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
            latestCommit.orElse(new ApiCommitModel("<default>", "0.0.0")));

        ApiCommitModel nextCommit = new ApiCommitModel(versionModel.getCommitId(), nextVersion.toVersionString());
        repoManagementApi.createCommit(apiParameterContainer, nextCommit, latestCommit.orElse(null));

        URI uri = URI.create(request.getRequestURL().toString() + "/" + nextCommit.getVersion());

        CreateVersionResponse response = new CreateVersionResponse(versionModel.getCommitId(), nextVersion.toVersionString());
        response.addLink(toLink(versionSelfLink(apiParameterContainer, nextVersion.toVersionString())));
        return ResponseEntity.created(uri).body(response);
    }

    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/version/{versionArg:.+}", method = RequestMethod.GET)
    public ResponseEntity<GetVersionResponse> findVersion(ApiParameterContainer apiParameterContainer,
                                                          @PathVariable("versionArg") String versionArg) {
        Optional<ApiCommitModel> commit = repoManagementApi.findCommit(apiParameterContainer, versionArg);
        if (commit.isPresent()) {
            GetVersionResponse versionResponse = new GetVersionResponse(commit.get().getCommitId(), commit.get().getVersion());
            versionResponse.addLink(toLink(versionSelfLink(apiParameterContainer, commit.get().getVersion())));
            versionResponse.addLink(toLink(versionListLink(apiParameterContainer, VERSION_REF)));
            versionResponse.addLink(toLink(repositoryLink(apiParameterContainer, REPOSITORY_REF)));
            return ResponseEntity.ok(versionResponse);
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