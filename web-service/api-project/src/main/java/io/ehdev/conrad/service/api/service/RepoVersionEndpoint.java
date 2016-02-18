package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.CommitNotFoundException;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.comparator.ReverseApiCommitComparator;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.model.version.VersionCreateModel;
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import io.ehdev.conrad.service.api.aop.annotation.ReadPermissionRequired;
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired;
import io.ehdev.conrad.service.api.aop.annotation.WritePermissionRequired;
import io.ehdev.conrad.service.api.service.model.version.CreateVersionResponse;
import io.ehdev.conrad.service.api.service.model.version.GetAllVersionsCommitResponse;
import io.ehdev.conrad.service.api.service.model.version.GetAllVersionsResponse;
import io.ehdev.conrad.service.api.service.model.version.GetVersionResponse;
import io.ehdev.conrad.version.bumper.api.VersionBumperService;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.VersionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
                GetAllVersionsCommitResponse commit = new GetAllVersionsCommitResponse(it.getCommitId(), it.getVersion());
                commit.add(versionSelfLink(apiParameterContainer, it.getVersion()));
                response.addCommits(commit);
            });

        return ResponseEntity.ok(response);
    }

    @LoggedInUserRequired
    @WritePermissionRequired
    @RepoRequired(exists = true)
    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public ResponseEntity<CreateVersionResponse> createNewVersion(ApiParameterContainer apiParameterContainer,
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

        CreateVersionResponse response = new CreateVersionResponse(versionModel.getCommitId(), nextVersion.toVersionString());
        response.add(versionSelfLink(apiParameterContainer, nextVersion.toVersionString()));
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
            versionResponse.add(versionSelfLink(apiParameterContainer, commit.get().getVersion()));
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

    public static Link versionSelfLink(ApiParameterContainer container, String version) {
        return linkTo(methodOn(RepoVersionEndpoint.class, container.getProjectName(), container.getRepoName())
            .findVersion(container, version)).withSelfRel();
    }

}
