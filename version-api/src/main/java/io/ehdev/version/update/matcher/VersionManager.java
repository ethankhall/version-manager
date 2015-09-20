package io.ehdev.version.update.matcher;

import io.ehdev.version.commit.CommitDetails;
import io.ehdev.version.commit.RepositoryCommit;
import io.ehdev.version.commit.model.RepositoryCommitModel;
import io.ehdev.version.commit.model.ScmMetaDataModel;
import io.ehdev.version.repository.CommitModelRepository;
import io.ehdev.version.repository.ScmMetaDataRepository;
import io.ehdev.version.update.NextVersion;
import io.ehdev.version.update.VersionBumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Transactional
public class VersionManager {

    private static final Logger log = LoggerFactory.getLogger(VersionManager.class);

    final CommitModelRepository commitRepository;
    final ScmMetaDataRepository scmMetaDataRepository;
    final Map<String, VersionBumper> versionBumpers = new HashMap<>();
    
    @Autowired
    public VersionManager(CommitModelRepository commitRepository,
                          ScmMetaDataRepository scmMetaDataRepository,
                          final Set<VersionBumper> bumperSet) {
        bumperSet.forEach(bumper -> versionBumpers.put(bumper.name(), bumper));
        this.commitRepository = commitRepository;
        this.scmMetaDataRepository = scmMetaDataRepository;
    }

    public RepositoryCommit claimVersion(CommitDetails commitDetails) {
        String parentCommitId = commitDetails.getParentCommitId();
        log.debug("[{}] Parent Commit ID: {}", commitDetails.getCommitId(), parentCommitId);

        RepositoryCommitModel parentCommit = commitRepository.findByCommitId(parentCommitId);
        String versionBumperName = parentCommit.getScmMetaDataModel().getVersionBumperName();
        log.debug("[{}] Version Bumpper Name: {}", commitDetails.getCommitId(), versionBumperName);


        VersionBumper versionBumper = versionBumpers.get(versionBumperName);
        NextVersion nextVersion = versionBumper.createNextVersion(parentCommit, commitDetails);

        RepositoryCommitModel nextCommit = new RepositoryCommitModel(commitDetails.getCommitId(), nextVersion.getCommitVersion());
        ScmMetaDataModel scmMetaData = scmMetaDataRepository.findByRepoName(commitDetails.getScmRepository());
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
