package io.ehdev.conrad.app.service.version;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.app.exception.VersionNotFoundException;
import io.ehdev.conrad.app.manager.CommitManager;
import io.ehdev.conrad.app.service.ApiFactory;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.VersionFactory;
import io.ehdev.conrad.backend.version.commit.internal.BumpLowestWithSnapshot;
import io.ehdev.conrad.database.model.CommitModel;
import io.ehdev.conrad.model.version.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api/version")
public class DefaultVersionService implements VersionService {

    private static final Logger log = LoggerFactory.getLogger(DefaultVersionService.class);

    public static final Comparator<CommitModel> REVERSE_ORDER = Comparator.<CommitModel>reverseOrder();
    final CommitManager commitManager;

    @Autowired
    public DefaultVersionService(CommitManager commitManager) {
        this.commitManager = commitManager;
    }

    @JsonView(VersionView.UnreleasedVersionView.class)
    @RequestMapping(value = "/{repoId}/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UncommitedVersionModel createUnreleasedVersion(@PathVariable("repoId") String repoId,
                                                          @RequestBody @Valid VersionSearchModel versionSearchModel) {
        CommitVersion version = commitManager.findVersion(UUID.fromString(repoId), versionSearchModel);
        if(version == null) {
            log.debug("Repo {} with commits {} gave null response, defaulting...", repoId, versionSearchModel.getCommits());
            version = VersionFactory.parse("0.0.0");
        }
        CommitVersion buildVersion = version.bump(new BumpLowestWithSnapshot());
        return ApiFactory.VersionModelFactory.create(buildVersion);
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @CacheEvict(value = "versionsForRepo", key = "#repoId")
    @RequestMapping(value = "/{repoId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public VersionCommitModel claimVersion(@PathVariable("repoId") String repoId,
                                           @Valid @RequestBody VersionCreateModel versionCreateModel) {
        UUID repoIdUuid = UUID.fromString(repoId);
        CommitModel currentCommit = commitManager.createCommit(repoIdUuid, versionCreateModel);

        return ApiFactory.VersionModelFactory.create(currentCommit.getCommitId(), currentCommit.getVersion());
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @Cacheable(value = "versionsForRepo", key = "#repoId")
    @RequestMapping(value = "/{repoId}", method = RequestMethod.GET)
    public RepoVersionModel findVersionsForRepo(@PathVariable("repoId") String repoId) {
        List<CommitModel> commitsForRepo = commitManager.findCommitsForRepo(UUID.fromString(repoId));
        List<VersionCommitModel> collect = commitsForRepo
            .stream().sorted(REVERSE_ORDER)
            .map(ApiFactory.VersionModelFactory::create)
            .collect(Collectors.toList());
        return new RepoVersionModel(collect);
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}/{commitId}", method = RequestMethod.GET)
    public VersionCommitModel findVersionForRepo(@PathVariable("repoId") String repoId, @PathVariable("commitId") String commitId) {
        CommitModel commit = commitManager.findCommit(UUID.fromString(repoId), commitId);
        if(null == commit) {
            throw new VersionNotFoundException();
        }
        return ApiFactory.VersionModelFactory.create(commit.getCommitId(), commit.getVersion());
    }

}
