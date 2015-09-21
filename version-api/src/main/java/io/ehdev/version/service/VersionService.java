package io.ehdev.version.service;

import io.ehdev.version.commit.CommitVersion;
import io.ehdev.version.commit.model.RepositoryCommitModel;
import io.ehdev.version.manager.VersionBumperManager;
import io.ehdev.version.manager.VersionManager;
import io.ehdev.version.repository.CommitModelRepository;
import io.ehdev.version.service.model.VersionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Transactional
@RestController("/api/version")
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
        if(null == parentCommit) {
            throw new VersionNotFoundException();
        }

        CommitVersion buildVersion = versionBumperManager.findVersionBumper(parentCommit).createBuildVersion(parentCommit);
        return new VersionResponse(repoId, buildVersion);
    }
}
