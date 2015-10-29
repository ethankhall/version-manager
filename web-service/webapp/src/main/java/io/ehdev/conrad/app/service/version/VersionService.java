package io.ehdev.conrad.app.service.version;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.app.manager.CommitManager;
import io.ehdev.conrad.app.service.ApiFactory;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.internal.BumpLowestWithSnapshot;
import io.ehdev.conrad.database.model.CommitModel;
import io.ehdev.conrad.model.version.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VersionService {

    public static final Comparator<CommitModel> REVERSE_ORDER = Comparator.<CommitModel>reverseOrder();
    final CommitManager commitManager;

    @Autowired
    public VersionService(CommitManager commitManager) {
        this.commitManager = commitManager;
    }

    @JsonView(VersionView.UnreleasedVersionView.class)
    @RequestMapping(value = "/{repoId}/search", method = RequestMethod.POST)
    VersionCommitModel createUnreleasedVersion(@PathVariable("repoId") String repoId,
                                               @RequestBody VersionSearchModel versionSearchModel) {
        CommitVersion version = commitManager.findVersion(UUID.fromString(repoId), versionSearchModel);
        CommitVersion buildVersion = version.bump(new BumpLowestWithSnapshot());
        return ApiFactory.VersionModelFactory.create(null, buildVersion);
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}", method = RequestMethod.POST)
    VersionCommitModel claimVersion(@PathVariable("repoId") String repoId,
                                    @Valid @RequestBody VersionCreateModel versionCreateModel) {
        UUID repoIdUuid = UUID.fromString(repoId);
        CommitModel currentCommit = commitManager.createCommit(repoIdUuid, versionCreateModel);

        return ApiFactory.VersionModelFactory.create(currentCommit.getCommitId(), currentCommit.getVersion());
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}", method = RequestMethod.GET)
    RepoVersionModel findVersionsForRepo(@PathVariable("repoId") String repoId) {
        List<CommitModel> commitsForRepo = commitManager.findCommitsForRepo(UUID.fromString(repoId));
        List<VersionCommitModel> collect = commitsForRepo
            .stream().sorted(REVERSE_ORDER)
            .map(ApiFactory.VersionModelFactory::create)
            .collect(Collectors.toList());
        return new RepoVersionModel(collect);
    }

}
