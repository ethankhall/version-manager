package io.ehdev.conrad.app.manager;

import io.ehdev.conrad.app.exception.UnauthorizedTokenException;
import io.ehdev.conrad.app.exception.VersionConflictException;
import io.ehdev.conrad.app.exception.VersionNotFoundException;
import io.ehdev.conrad.backend.commit.CommitDetails;
import io.ehdev.conrad.backend.commit.internal.DefaultCommitDetails;
import io.ehdev.conrad.backend.version.bumper.VersionBumper;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.VersionFactory;
import io.ehdev.conrad.database.impl.CommitModel;
import io.ehdev.conrad.database.impl.VcsRepoModel;
import io.ehdev.conrad.database.repository.CommitModelRepository;
import io.ehdev.conrad.database.repository.VcsRepoRepository;
import io.ehdev.conrad.model.version.VersionCreateModel;
import io.ehdev.conrad.model.version.VersionSearchModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CommitManager {

    @Autowired
    CommitModelRepository commitModelRepository;

    @Autowired
    VcsRepoRepository vcsRepoRepository;

    @Autowired
    BumperManager bumperManager;

    public CommitVersion findVersion(UUID repoId, VersionSearchModel versionSearchModel) {
        if(versionSearchModel.getCommits().isEmpty()) {
            return null;
        }
        CommitModel commitModel = findCommit(repoId, versionSearchModel);
        if(null == commitModel) {
            return null;
        } else {
            return commitModel.getVersion();
        }
    }

    public CommitModel findCommit(UUID repoId, String commitId) {
        return commitModelRepository.findByCommitIdAndRepoId(commitId, repoId);
    }

    public CommitModel findCommit(UUID repoId, VersionSearchModel versionSearchModel) {
        VcsRepoModel vcs = vcsRepoRepository.findOne(repoId);
        List<CommitModel> commits = commitModelRepository.findCommits(vcs, versionSearchModel.getCommits());

        Collections.sort(commits);

        return !commits.isEmpty() ? commits.get(commits.size() - 1) : null;
    }

    public CommitModel createCommit(UUID repoId, VersionCreateModel versionCreateModel) {
        VcsRepoModel vcs = vcsRepoRepository.findOne(repoId);
        if (!StringUtils.equals(vcs.getToken(), versionCreateModel.getToken())) {
            throw new UnauthorizedTokenException();
        }
        VersionBumper versionBumper = bumperManager.findVersionBumper(repoId);

        CommitModel commit = findCommit(repoId, versionCreateModel);
        if(commit == null && commitModelRepository.countByRepoId(repoId) != 0) {
            throw new VersionNotFoundException();
        }

        CommitVersion nextVersion = findNextVersion(versionCreateModel, versionBumper, commit);

        if(commitModelRepository.findByRepoIdAndVersion(vcs, nextVersion) != null) {
            throw new VersionConflictException(nextVersion.toVersionString());
        }

        return commitModelRepository.save(new CommitModel(versionCreateModel.getCommitId(), vcs, nextVersion, commit));
    }

    private CommitVersion findNextVersion(VersionCreateModel versionCreateModel, VersionBumper versionBumper, CommitModel commit) {
        if(commit == null) {
            return VersionFactory.parse("0.0.1");
        }
        return versionBumper.createNextVersion(commit.getVersion(), toCommitDetails(versionCreateModel));
    }

    public CommitDetails toCommitDetails(VersionCreateModel versionCreateModel) {
        return new DefaultCommitDetails(versionCreateModel.getCommitId(), versionCreateModel.getMessage());
    }

    public List<CommitModel> findCommitsForRepo(UUID repoId) {
        VcsRepoModel vcs = vcsRepoRepository.findOne(repoId);
        if(null == vcs) {
            throw new VersionNotFoundException();
        }
        return commitModelRepository.findCommits(vcs);
    }
}
