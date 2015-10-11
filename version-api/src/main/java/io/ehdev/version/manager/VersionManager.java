package io.ehdev.version.manager;

import io.ehdev.version.model.commit.CommitDetails;
import io.ehdev.version.model.commit.RepositoryCommit;
import io.ehdev.version.model.commit.internal.DefaultCommitVersion;
import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import io.ehdev.version.model.update.NextVersion;
import io.ehdev.version.model.update.VersionBumper;
import io.ehdev.version.model.repository.CommitModelRepository;
import io.ehdev.version.model.repository.ScmMetaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class VersionManager {

    private static final Logger log = LoggerFactory.getLogger(VersionManager.class);

    final CommitModelRepository commitRepository;
    final ScmMetaDataRepository scmMetaDataRepository;
    final VersionBumperManager versionBumperManager;
    
    @Autowired
    public VersionManager(CommitModelRepository commitRepository,
                          ScmMetaDataRepository scmMetaDataRepository,
                          VersionBumperManager versionBumperManager) {
        this.commitRepository = commitRepository;
        this.scmMetaDataRepository = scmMetaDataRepository;
        this.versionBumperManager = versionBumperManager;
    }

    public RepositoryCommit claimVersion(CommitDetails commitDetails) {
        String parentCommitId = commitDetails.getParentCommitId();
        log.debug("[{}] Parent Commit ID: {}", commitDetails.getCommitId(), parentCommitId);
        UUID repoUuid = UUID.fromString(commitDetails.getScmRepositoryId());

        RepositoryCommitModel parentCommit = commitRepository.findByCommitIdAndRepoId(parentCommitId, repoUuid);
        if(parentCommit == null && commitRepository.countByRepoId(repoUuid) == 0) {
            parentCommit = commitRepository.save(new RepositoryCommitModel("00000000", new DefaultCommitVersion(0, 0, 0)));
        } else if(parentCommit == null) {
            throw new RuntimeException();
        }

        VersionBumper versionBumper = versionBumperManager.findVersionBumper(parentCommit);
        NextVersion nextVersion = versionBumper.createNextVersion(parentCommit, commitDetails);

        RepositoryCommitModel nextCommit = new RepositoryCommitModel(commitDetails.getCommitId(), nextVersion.getCommitVersion());
        ScmMetaDataModel scmMetaData = scmMetaDataRepository.findByUuid(repoUuid);
        nextCommit.setScmMetaDataModel(scmMetaData);

        if(nextVersion.getType() == NextVersion.Type.NEXT) {
            parentCommit.setNextCommit(nextCommit);
        } else {
            parentCommit.setBugfixCommit(nextCommit);
        }

        commitRepository.save(parentCommit);
        return commitRepository.save(nextCommit);
    }
}
