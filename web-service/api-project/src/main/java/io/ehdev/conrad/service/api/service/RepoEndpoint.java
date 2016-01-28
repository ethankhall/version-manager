package io.ehdev.conrad.service.api.service;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiQualifiedRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitIdModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
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
import org.springframework.stereotype.Controller;
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

@Controller
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

    @RequestMapping(value = "/{repoName}/details", method = RequestMethod.GET)
    public ResponseEntity<RestRepoDetailsModel> getRepoDetails(ApiQualifiedRepoModel repoModel,
                                                               @Valid @NotNull ConradUser user) {
        return ResponseEntity.ok(toRestModel(repoManagementApi.getDetails(repoModel).get()));
    }

    @RequestMapping(value = "/{repoName}/versions", method = RequestMethod.GET)
    public ResponseEntity<RestVersionCollectionModel> getAllVersions(ApiQualifiedRepoModel repoModel,
                                                                     @Valid @NotNull ConradUser user) {
        Stream<RestCommitModel> restCommitStream = repoManagementApi
            .findAllCommits(repoModel)
            .stream()
            .map(ConversionUtility::toRestModel);

        List<RestCommitModel> commits = restCommitStream.collect(Collectors.toList());
        RestVersionCollectionModel model = new RestVersionCollectionModel(commits, restCommitStream.findFirst().orElseGet(null));
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value = "/{repoName}/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> createNewVersion(ApiQualifiedRepoModel repoModel,
                                                            @RequestBody VersionCreateModel versionModel,
                                                            HttpServletRequest request,
                                                            @Valid @NotNull ConradUser user) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitIdModel::new)
            .collect(Collectors.toList());

        ApiFullCommitModel latestCommit = versionBumperService.findLatestCommitVersion(repoModel, commits);

        CommitVersion nextVersion = versionBumperService.findNextVersion(
            repoModel,
            versionModel.getCommitId(),
            versionModel.getMessage(),
            VersionFactory.parse(latestCommit));

        ApiFullCommitModel nextCommit = new ApiFullCommitModel(versionModel.getCommitId(), nextVersion.toVersionString());
        repoManagementApi.createCommit(repoModel, nextCommit, latestCommit);

        URI uri = URI.create(request.getRequestURL().toString() + "/" + nextCommit.getVersion());
        return ResponseEntity.created(uri).body(toRestModel(nextCommit));
    }

    @RequestMapping(value = "/{repoName}/search/version", method = RequestMethod.POST)
    public ResponseEntity<RestCommitModel> findVersion(ApiQualifiedRepoModel repoModel,
                                                       @RequestBody VersionCreateModel versionModel,
                                                       @Valid @NotNull ConradUser user) {
        List<ApiCommitModel> commits = versionModel.getCommits()
            .stream()
            .map(ApiCommitIdModel::new)
            .collect(Collectors.toList());

        ApiFullCommitModel latest = versionBumperService.findLatestCommitVersion(repoModel, commits);
        if (null == latest) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            return ResponseEntity.ok(toRestModel(latest));
        }
    }

    @RequestMapping(value = "/{repoName}/version/{versionArg}", method = RequestMethod.GET)
    public ResponseEntity<RestCommitModel> findVersion(ApiQualifiedRepoModel repoModel,
                                                          @RequestParam("versionArg") String versionArg,
                                                          @Valid @NotNull ConradUser user) {
        Optional<ApiFullCommitModel> commit = repoManagementApi.findCommit(repoModel, versionArg);
        if(commit.isPresent()) {
            return ResponseEntity.ok(toRestModel(commit.get()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
