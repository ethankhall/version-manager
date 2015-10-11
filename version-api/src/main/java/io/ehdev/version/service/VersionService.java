package io.ehdev.version.service;

import io.ehdev.version.manager.VersionBumperManager;
import io.ehdev.version.manager.VersionManager;
import io.ehdev.version.model.commit.CommitVersion;
import io.ehdev.version.model.commit.RepositoryCommit;
import io.ehdev.version.model.commit.internal.DefaultCommitVersion;
import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import io.ehdev.version.model.repository.CommitModelRepository;
import io.ehdev.version.model.repository.ScmMetaDataRepository;
import io.ehdev.version.service.exception.VersionNotFoundException;
import io.ehdev.version.service.model.VersionCreation;
import io.ehdev.version.service.model.VersionResponse;
import io.ehdev.version.service.model.VersionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    ScmMetaDataRepository scmMetaDataRepository;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    VersionResponse getVersionForRepo(@RequestBody VersionSearch versionSearch) {
        UUID repoId = UUID.fromString(versionSearch.getRepoId());
        ScmMetaDataModel metaDataModel = scmMetaDataRepository.findByUuid(repoId);

        List<RepositoryCommitModel> commits = commitModelRepository.findCommits(metaDataModel, versionSearch.getCommitIds());
        Collections.sort(commits);

        RepositoryCommitModel parentCommit = !commits.isEmpty() ? commits.get(0) : null;
        if(parentCommit != null) {
            CommitVersion buildVersion = versionBumperManager.findVersionBumper(parentCommit).createBuildVersion(parentCommit);
            return new VersionResponse(repoId.toString(), buildVersion);
        }
        if(commitModelRepository.countByRepoId(repoId) == 0) {
            return new VersionResponse(repoId.toString(), new DefaultCommitVersion(0, 0, 1).asSnapshot());
        }
        throw new VersionNotFoundException();
    }

    @RequestMapping(method = RequestMethod.POST)
    VersionResponse createNewCommitRevision(@RequestBody VersionCreation newVersion) {
        RepositoryCommit repositoryCommit = versionManager.claimVersion(newVersion.toCommitDetails());
        return new VersionResponse(newVersion.getRepoId(), repositoryCommit.getVersion());
    }
}
