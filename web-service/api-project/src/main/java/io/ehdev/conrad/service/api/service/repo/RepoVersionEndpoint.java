package io.ehdev.conrad.service.api.service.repo;

import io.ehdev.conrad.model.version.CreateVersionRequest;
import io.ehdev.conrad.model.version.CreateVersionResponse;
import io.ehdev.conrad.model.version.GetAllVersionsResponse;
import io.ehdev.conrad.model.version.GetVersionResponse;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
import io.ehdev.conrad.service.api.service.annotation.InternalLink;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tech.crom.business.api.CommitApi;
import tech.crom.business.api.VersionBumperApi;
import tech.crom.database.api.CommitManager;
import tech.crom.database.api.RepoManager;
import tech.crom.model.commit.CommitIdContainer;
import tech.crom.model.commit.CromCommitDetails;
import tech.crom.service.api.ReverseApiCommitComparator;
import tech.crom.version.bumper.model.CommitModel;
import tech.crom.version.bumper.model.ReservedVersionModel;
import tech.crom.model.commit.VersionDetails;
import tech.crom.web.api.model.RequestDetails;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.toLink;
import static io.ehdev.conrad.service.api.service.model.LinkUtilities.versionSelfLink;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
public class RepoVersionEndpoint {

    private final CommitApi commitApi;
    private final RepoManager repoManager;
    private final VersionBumperApi versionBumperApi;

    @Autowired
    public RepoVersionEndpoint(CommitApi commitApi, RepoManager repoManager, VersionBumperApi versionBumperManager) {
        this.commitApi = commitApi;
        this.repoManager = repoManager;
        this.versionBumperApi = versionBumperManager;
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

        commitApi.findAllCommits(requestDetails.getCromRepo())
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
        List<CommitIdContainer> commits = versionModel.getCommits()
            .stream()
            .map(CommitIdContainer::new)
            .collect(Collectors.toList());


        CromCommitDetails latestCommit = commitApi.findLatestCommit(requestDetails.getCromRepo(), commits);

        assertHistoryIsNotMissing(commits, latestCommit);

        CommitModel commitModel = new CommitModel(versionModel.getCommitId(), versionModel.getMessage(), ZonedDateTime.now(ZoneOffset.UTC));
        ReservedVersionModel reservedVersionModel = versionBumperApi
            .findVersionBumper(requestDetails.getCromRepo())
            .calculateNextVersion(commitModel, latestCommit == null ? null : new VersionDetails(latestCommit.getVersion()));

        commitApi.createCommit()
        CommitVersion nextVersion = versionBumperApi.findNextVersion(
            requestDetails.getResourceDetails(),
            versionModel.getCommitId(),
            versionModel.getMessage(),
            latestCommit.orElse(new ApiCommitModel("<default>", "0.0.0", ZonedDateTime.now())));

        ApiCommitModel nextCommit = new ApiCommitModel(versionModel.getCommitId(),
            nextVersion.toVersionString(),
            nextVersion.getCreatedAt());
        repoManager.createCommit(requestDetails.getResourceDetails(), nextCommit, latestCommit.orElse(null));

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
        Optional<ApiCommitModel> commit = repoManager.findCommit(requestDetails.getResourceDetails(), versionArg);
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

    private void assertHistoryIsNotMissing(List<CommitManager.CommitSearch> commits, CromCommitDetails latestCommit) {
        if (!commits.isEmpty() && latestCommit != null) {
            String joinedCommit = commits.stream().map(CommitManager.CommitSearch::getCommitId).collect(Collectors.joining(", "));
            throw new CommitNotFoundException(joinedCommit);
        }
    }

    private class CommitNotFoundException extends RuntimeException {
        public CommitNotFoundException(String joinedCommit) {
            super(joinedCommit);
        }
    }
}
