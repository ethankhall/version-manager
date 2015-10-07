package io.ehdev.version;

import io.ehdev.version.model.commit.internal.DefaultCommitVersion;
import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import io.ehdev.version.model.commit.model.VersionBumperModel;
import io.ehdev.version.model.repository.CommitModelRepository;
import io.ehdev.version.model.repository.ScmMetaDataRepository;
import io.ehdev.version.model.repository.VersionBumperRepository;
import io.ehdev.version.model.update.semver.SemverVersionBumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader {
    public static final String NO_CHILDREN_COMMIT_ID = "noChildrenCommit";
    public static final String PARENT_COMMIT_ID = "parentCommit";
    public static final String CHILD_COMMIT_ID = "nextChildCommit";
    public static final String BUGFIX_COMMIT_ID = "nextBugfixCommit";

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

        model1.className = SemverVersionBumper.class.getSimpleName();
        model1.bumperName = 'SemVer'
        model1.description = 'SemVer'
        VersionBumperModel bumper = versionBumperRepository.saveAndFlush(model1);
        noChildren(bumper);
        hasNext(bumper);
        hasBuild(bumper);
    }

    private void noChildren(VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = createScmMetaData('no-children', bumper)

        createCommit(scmMetaData, NO_CHILDREN_COMMIT_ID, 3)
    }

    private RepositoryCommitModel createCommit(ScmMetaDataModel scmMetaData, String commitId, Integer patchVersion, Integer build = 0) {
        def version = new DefaultCommitVersion(1, 2, patchVersion, build)
        RepositoryCommitModel noChildrenCommit = new RepositoryCommitModel(commitId, version);
        noChildrenCommit.setScmMetaDataModel(scmMetaData);
        commitRepository.save(noChildrenCommit);
        return noChildrenCommit
    }

    private ScmMetaDataModel createScmMetaData(String repoId, VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = new ScmMetaDataModel();

        scmMetaData.setUuid(repoId);
        scmMetaData.setRepoName(repoId);
        scmMetaData.setVersionBumperModel(bumper);
        scmMetaData = scmMetaDataRepository.save(scmMetaData);
        return scmMetaData
    }

    private void hasNext(VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = createScmMetaData('next', bumper)

        RepositoryCommitModel childCommit = createCommit(scmMetaData, CHILD_COMMIT_ID, 4)
        RepositoryCommitModel parentCommit = createCommit(scmMetaData, PARENT_COMMIT_ID, 3)

        parentCommit.setNextCommit(childCommit);
        commitRepository.save(parentCommit);
    }

    private void hasBuild(VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = createScmMetaData('build', bumper)

        RepositoryCommitModel childCommit = createCommit(scmMetaData, CHILD_COMMIT_ID, 4)
        RepositoryCommitModel buildCommit = createCommit(scmMetaData, BUGFIX_COMMIT_ID, 3, 1)
        RepositoryCommitModel parentCommit = createCommit(scmMetaData, PARENT_COMMIT_ID, 3)

        parentCommit.setNextCommit(childCommit);
        parentCommit.setBugfixCommit(buildCommit)
        commitRepository.save(parentCommit);
    }
}
