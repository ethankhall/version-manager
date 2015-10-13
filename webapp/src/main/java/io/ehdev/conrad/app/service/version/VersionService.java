package io.ehdev.conrad.app.service.version;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.app.manager.CommitManager;
import io.ehdev.conrad.app.service.version.model.VersionCreateModel;
import io.ehdev.conrad.app.service.version.model.VersionResponseModel;
import io.ehdev.conrad.app.service.version.model.VersionSearchModel;
import io.ehdev.conrad.app.service.version.model.VersionView;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.internal.BumpLowestWithSnapshot;
import io.ehdev.conrad.database.model.CommitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api/version")
public class VersionService {

    final CommitManager commitManager;

    @Autowired
    public VersionService(CommitManager commitManager) {
        this.commitManager = commitManager;
    }

    @JsonView(VersionView.UnreleasedVersionView.class)
    @RequestMapping(value = "/{repoId}/search", method = RequestMethod.POST)
    VersionResponseModel createUnreleasedVersion(@PathVariable("repoId") String repoId,
                                                 @RequestBody VersionSearchModel versionSearchModel) {
        CommitVersion version = commitManager.findVersion(UUID.fromString(repoId), versionSearchModel);
        CommitVersion buildVersion = version.bump(new BumpLowestWithSnapshot());
        return new VersionResponseModel(buildVersion);
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}", method = RequestMethod.POST)
    VersionResponseModel claimVersion(@PathVariable("repoId") String repoId,
                                      @Valid @RequestBody VersionCreateModel versionCreateModel) {
        UUID repoIdUuid = UUID.fromString(repoId);
        CommitModel currentCommit = commitManager.createCommit(repoIdUuid, versionCreateModel);

        return new VersionResponseModel(currentCommit.getCommitId(), currentCommit.getVersion());
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}", method = RequestMethod.GET)
    Map<String, VersionResponseModel> findVersionsForRepo(@PathVariable("repoId") String repoId) {
        List<CommitModel> commitsForRepo = commitManager.findCommitsForRepo(UUID.fromString(repoId));
        return commitsForRepo.stream().collect(Collectors.toMap(CommitModel::getCommitId, VersionResponseModel::new));
    }

}
