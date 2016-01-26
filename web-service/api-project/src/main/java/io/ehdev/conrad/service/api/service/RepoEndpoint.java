package io.ehdev.conrad.service.api.service;

import com.sun.istack.internal.NotNull;
import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.model.project.ApiQualifiedRepoModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitIdModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import io.ehdev.conrad.model.rest.RestCommitModel;
import io.ehdev.conrad.model.rest.RestRepoDetailsModel;
import io.ehdev.conrad.model.rest.RestVersionCollectionModel;
import io.ehdev.conrad.model.user.ConradUser;
import io.ehdev.conrad.model.version.VersionCommitModel;
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
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<RestRepoDetailsModel> getRepoDetails(@RequestParam("projectName") String projectName,
                                                               @RequestParam("repoName") String repoName,
                                                               @Valid @NotNull ConradUser user) {
        ApiQualifiedRepoModel repoModel = new ApiQualifiedRepoModel(projectName, repoName);
        Optional<ApiRepoDetailsModel> details = repoManagementApi.getDetails(repoModel);
        if(details.isPresent()) {
            return ResponseEntity.ok(ConversionUtility.toRestModel(details.get()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{repoName}/versions", method = RequestMethod.GET)
    public ResponseEntity<RestVersionCollectionModel> getAllVersions(@RequestParam("projectName") String projectName,
                                                                     @RequestParam("repoName") String repoName,
                                                                     @Valid @NotNull ConradUser user) {
        ApiQualifiedRepoModel repoModel = new ApiQualifiedRepoModel(projectName, repoName);
        if(repoManagementApi.doesRepoExist(repoModel)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        Stream<RestCommitModel> restCommitStream = repoManagementApi
            .findAllCommits(repoModel)
            .stream()
            .map(ConversionUtility::toRestModel);

        List<RestCommitModel> commits = restCommitStream.collect(Collectors.toList());
        RestVersionCollectionModel model = new RestVersionCollectionModel(commits, restCommitStream.findFirst().orElseGet(null));
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value = "/{repoName}/version", method = RequestMethod.POST)
    public ResponseEntity<VersionCommitModel> createNewVersion(@RequestParam("projectName") String projectName,
                                                               @RequestParam("repoName") String repoName,
                                                               @RequestBody VersionCreateModel versionModel,
                                                               HttpServletRequest request,
                                                               @Valid @NotNull ConradUser user) {
        ApiQualifiedRepoModel repoModel = new ApiQualifiedRepoModel(projectName, repoName);
        Optional<ApiRepoDetailsModel> details = repoManagementApi.getDetails(repoModel);
        if(!details.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

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

        VersionCommitModel response = new VersionCommitModel(
            versionModel.getCommitId(),
            nextVersion.toVersionString(),
            nextVersion.getVersionGroup(),
            nextVersion.getPostFix());

        URI uri = URI.create(request.getRequestURL().toString() + "/" + response.getVersion());
        return ResponseEntity.created(uri).body(response);
    }

    @RequestMapping(value = "/{repoName}/search/version", method = RequestMethod.POST)
    public ResponseEntity<VersionCommitModel> findVersion(@RequestParam("projectName") String projectName,
                                                          @RequestParam("repoName") String repoName,
                                                          @RequestBody VersionCreateModel versionCreateModel,
                                                          @Valid @NotNull ConradUser user) {
        return null;
    }

    @RequestMapping(value = "/{repoName}/version/{versionArg}", method = RequestMethod.GET)
    public ResponseEntity<VersionCommitModel> findVersion(@RequestParam("projectName") String projectName,
                                                          @RequestParam("repoName") String repoName,
                                                          @RequestParam("versionArg") String versionArg,
                                                          @Valid @NotNull ConradUser user) {
        return null;
    }
}
