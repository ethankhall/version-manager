package io.ehdev.version.service;

import io.ehdev.version.model.commit.CommitVersion;
import io.ehdev.version.model.commit.RepositoryCommit;
import io.ehdev.version.model.commit.internal.DefaultCommitVersion;
import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import io.ehdev.version.manager.VersionBumperManager;
import io.ehdev.version.manager.VersionManager;
import io.ehdev.version.model.repository.CommitModelRepository;
import io.ehdev.version.service.exception.VersionNotFoundException;
import io.ehdev.version.service.model.VersionCreation;
import io.ehdev.version.service.model.VersionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/api/version")
public class VersionService {

    @Autowired
    VersionManager versionManager;

    @Autowired
    CommitModelRepository commitModelRepository;

    @Autowired
    VersionBumperManager versionBumperManager;

    @RequestMapping(method = RequestMethod.GET, params = {"repoId", "parentCommit"})
    VersionResponse getVersionForRepo(@RequestParam("repoId") String repoId, @RequestParam("parentCommit") String originCommit) {
        RepositoryCommitModel parentCommit = commitModelRepository.findByCommitIdAndRepoId(originCommit, repoId);
        if(commitModelRepository.countByRepoId(repoId) == 0) {
            return new VersionResponse(repoId, new DefaultCommitVersion(0, 0, 1).asSnapshot());
        }
        if(null == parentCommit) {
            throw new VersionNotFoundException();
        }

        CommitVersion buildVersion = versionBumperManager.findVersionBumper(parentCommit).createBuildVersion(parentCommit);
        return new VersionResponse(repoId, buildVersion);
    }

    @RequestMapping(method = RequestMethod.POST)
    VersionResponse createNewCommitRevision(@RequestBody VersionCreation newVersion) {
        RepositoryCommit repositoryCommit = versionManager.claimVersion(newVersion.toCommitDetails());
        return new VersionResponse(newVersion.getRepoId(), repositoryCommit.getVersion());
    }
}
