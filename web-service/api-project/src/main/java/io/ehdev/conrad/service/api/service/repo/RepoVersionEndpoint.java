package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.comparator.ReverseApiCommitComparator;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.repo.RequestDetails;
import io.ehdev.conrad.model.version.CreateVersionRequest;
import io.ehdev.conrad.model.version.CreateVersionResponse;
import io.ehdev.conrad.model.version.GetAllVersionsResponse;
import io.ehdev.conrad.model.version.GetVersionResponse;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.toLink;
import static io.ehdev.conrad.service.api.service.model.LinkUtilities.versionSelfLink;

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

    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../../.."),
        @InternalLink(name = "repository", ref = "/..")
    })
    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public ResponseEntity<GetAllVersionsResponse> getAllVersions(RequestDetails requestDetails) {
        GetAllVersionsResponse response = new GetAllVersionsResponse();

        repoManagementApi
            .findAllCommits(requestDetails.getResourceDetails())
            .stream()
            .sorted(new ReverseApiCommitComparator())
            .forEach(it -> {
                GetAllVersionsResponse.CommitModel commit = new GetAllVersionsResponse.CommitModel(it.getCommitId(),
                    it.getVersion(),
                    it.getCreatedAt());
                commit.addLink(toLink(versionSelfLink(requestDetails, it.getVersion())));
                response.addCommit(commit);
            });

        if (!response.getCommits().isEmpty()) {
            response.setLatest(response.getCommits().get(0));
        }

        return ResponseEntity.ok(response);
    }

    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../../.."),
        @InternalLink(name = "versions", ref = "/../versions"),
        @InternalLink(name = "repository", ref = "/..")
    })
    @WritePermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public ResponseEntity<CreateVersionResponse> createNewVersion(RequestDetails requestDetails,
                                                                  @RequestBody CreateVersionRequest versionModel,
                                                                  HttpServletRequest request) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitModel::new)
            .collect(Collectors.toList());

        Optional<ApiCommitModel> latestCommit = repoManagementApi.findLatestCommit(requestDetails.getResourceDetails(), commits);

        assertHistoryIsNotMissing(commits, latestCommit);

        CommitVersion nextVersion = versionBumperService.findNextVersion(
            requestDetails.getResourceDetails(),
            versionModel.getCommitId(),
            versionModel.getMessage(),
            latestCommit.orElse(new ApiCommitModel("<default>", "0.0.0", ZonedDateTime.now())));

        ApiCommitModel nextCommit = new ApiCommitModel(versionModel.getCommitId(),
            nextVersion.toVersionString(),
            nextVersion.getCreatedAt());
        repoManagementApi.createCommit(requestDetails.getResourceDetails(), nextCommit, latestCommit.orElse(null));

        URI uri = URI.create(request.getRequestURL().toString() + "/" + nextCommit.getVersion());

        CreateVersionResponse response = new CreateVersionResponse(versionModel.getCommitId(),
            nextVersion.toVersionString(),
            nextVersion.getCreatedAt());
        response.addLink(toLink(versionSelfLink(requestDetails, nextVersion.toVersionString())));
        return ResponseEntity.created(uri).body(response);
    }

    @InternalLinks(links = {
        @InternalLink(name = "project", ref = "/../../../.."),
        @InternalLink(name = "versions", ref = "/../../versions"),
        @InternalLink(name = "repository", ref = "/../..")
    })
    @ReadPermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/version/{versionArg:.+}", method = RequestMethod.GET)
    public ResponseEntity<GetVersionResponse> findVersion(RequestDetails requestDetails,
                                                          @PathVariable("versionArg") String versionArg) {
        Optional<ApiCommitModel> commit = repoManagementApi.findCommit(requestDetails.getResourceDetails(), versionArg);
        if (commit.isPresent()) {
            ApiCommitModel apiCommitModel = commit.get();
            GetVersionResponse versionResponse = new GetVersionResponse(apiCommitModel.getCommitId(),
                apiCommitModel.getVersion(),
                apiCommitModel.getCreatedAt());
            return ResponseEntity.ok(versionResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void assertHistoryIsNotMissing(List<ApiCommitModel> commits, Optional<ApiCommitModel> latestCommit) {
        if (!commits.isEmpty() && !latestCommit.isPresent()) {
            String joinedCommit = commits.stream().map(ApiCommitModel::getCommitId).reduce((t, u) -> t + "," + u).get();
            throw new CommitNotFoundException(joinedCommit);
        }
    }
}
