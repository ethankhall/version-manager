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
import tech.crom.model.commit.CommitIdContainer;
import tech.crom.model.commit.impl.PersistedCommit;
import tech.crom.model.commit.impl.RequestedCommit;
import tech.crom.service.api.ReverseApiCommitComparator;
import tech.crom.web.api.model.RequestDetails;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static io.ehdev.conrad.service.api.service.model.LinkUtilities.toLink;
import static io.ehdev.conrad.service.api.service.model.LinkUtilities.versionSelfLink;

@Service
@RequestMapping("/api/v1/project/{projectName}/repo/{repoName}")
public class RepoVersionEndpoint {

    private final CommitApi commitApi;

    @Autowired
    public RepoVersionEndpoint(CommitApi commitApi) {
        this.commitApi = commitApi;
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
                    it.getVersionString(),
                    it.getCreatedAt());
                commit.addLink(toLink(versionSelfLink(requestDetails, it.getVersionString())));
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

        RequestedCommit commitModel = new RequestedCommit(versionModel.getCommitId(), versionModel.getMessage(), null);
        PersistedCommit nextCommit = commitApi.createCommit(requestDetails.getCromRepo(), commitModel, commits);

        URI uri = URI.create(request.getRequestURL().toString() + "/" + nextCommit.getVersionString());

        CreateVersionResponse response = new CreateVersionResponse(versionModel.getCommitId(),
            nextCommit.getVersionString(),
            nextCommit.getCreatedAt());
        response.addLink(toLink(versionSelfLink(requestDetails, nextCommit.getVersionString())));
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
        PersistedCommit commit = commitApi.findCommit(requestDetails.getCromRepo(), new CommitIdContainer(versionArg));
        if(null == commit) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GetVersionResponse versionResponse = new GetVersionResponse(commit.getCommitId(),
            commit.getVersionString(),
            commit.getCreatedAt());
        return ResponseEntity.ok(versionResponse);
    }
}
