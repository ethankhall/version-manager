package io.ehdev.version;

import io.ehdev.version.commit.internal.DefaultCommitVersion;
import io.ehdev.version.commit.model.RepositoryCommitModel;
import io.ehdev.version.commit.model.ScmMetaDataModel;
import io.ehdev.version.commit.model.VersionBumperModel;
import io.ehdev.version.repository.CommitModelRepository;
import io.ehdev.version.repository.ScmMetaDataRepository;
import io.ehdev.version.repository.VersionBumperRepository;
import io.ehdev.version.update.semver.SemverVersionBumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader {
    public static final String NO_CHILDREN_COMMIT_ID = "noChildrenCommit";
    public static final String PARENT_COMMIT_ID = "parentCommit";
    public static final String CHILD_COMMIT_ID = "nextChildCommit";

    private final CommitModelRepository commitRepository;
    private final ScmMetaDataRepository scmMetaDataRepository;
    private final VersionBumperRepository versionBumperRepository;

    @Autowired
    public TestDataLoader(CommitModelRepository commitRepository, ScmMetaDataRepository scmMetaDataRepository, VersionBumperRepository versionBumperRepository) {
        this.commitRepository = commitRepository;
        this.scmMetaDataRepository = scmMetaDataRepository;
        this.versionBumperRepository = versionBumperRepository;
    }

    public void loadData() {
        VersionBumperModel model1 = new VersionBumperModel();

        model1.setBumperName(SemverVersionBumper.class.getSimpleName());
        VersionBumperModel bumper = versionBumperRepository.saveAndFlush(model1);
        noChildren(bumper);
        hasNext(bumper);
    }

    private void noChildren(VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = new ScmMetaDataModel();

        scmMetaData.setRepoName("no-children");
        scmMetaData.setVersionBumperModel(bumper);
        scmMetaData = scmMetaDataRepository.save(scmMetaData);


        RepositoryCommitModel noChildrenCommit = new RepositoryCommitModel(NO_CHILDREN_COMMIT_ID, new DefaultCommitVersion(1, 2, 3));
        noChildrenCommit.setScmMetaDataModel(scmMetaData);
        commitRepository.save(noChildrenCommit);
    }

    private void hasNext(VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = new ScmMetaDataModel();

        scmMetaData.setRepoName("next");
        scmMetaData.setVersionBumperModel(bumper);
        scmMetaData = scmMetaDataRepository.save(scmMetaData);


        RepositoryCommitModel childCommit = new RepositoryCommitModel(CHILD_COMMIT_ID, new DefaultCommitVersion(1, 2, 4));
        RepositoryCommitModel parentCommit = new RepositoryCommitModel(PARENT_COMMIT_ID, new DefaultCommitVersion(1, 2, 3));
        parentCommit.setNextCommit(childCommit);
        childCommit.setScmMetaDataModel(scmMetaData);
        parentCommit.setScmMetaDataModel(scmMetaData);
        commitRepository.save(childCommit);
        commitRepository.save(parentCommit);
    }
}
