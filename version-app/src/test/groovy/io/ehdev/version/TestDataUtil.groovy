package io.ehdev.version

import groovy.transform.TupleConstructor
import io.ehdev.version.model.CommitVersion
import io.ehdev.conrad.model.commit.internal.DefaultCommitDetails

import io.ehdev.conrad.model.commit.model.RepositoryCommitModel
import io.ehdev.conrad.model.commit.model.ScmMetaDataModel
import io.ehdev.conrad.model.commit.model.VersionBumperModel
import io.ehdev.conrad.model.repository.CommitModelRepository
import io.ehdev.conrad.model.repository.ScmMetaDataRepository
import io.ehdev.conrad.model.repository.VersionBumperRepository
import io.ehdev.conrad.model.update.semver.SemverVersionBumper
import org.apache.commons.lang3.RandomStringUtils

class TestDataUtil {

    private final static String DEFAULT_VERSION_BUMPER_NAME = 'SemVer'

    final CommitModelRepository commitRepository;

    final ScmMetaDataRepository scmMetaDataRepository;

    final VersionBumperRepository versionBumperRepository

    final VersionBumperModel defaultBumper;

    TestDataUtil(VersionBumperRepository versionBumperRepository, ScmMetaDataRepository scmMetaDataRepository, CommitModelRepository commitRepository) {
        this.versionBumperRepository = versionBumperRepository
        this.scmMetaDataRepository = scmMetaDataRepository
        this.commitRepository = commitRepository
        this.defaultBumper = findDefaultVersionBumper(versionBumperRepository)
    }

    static VersionBumperModel findDefaultVersionBumper(VersionBumperRepository versionBumperRepository) {

        def bumper = versionBumperRepository.findByBumperName(DEFAULT_VERSION_BUMPER_NAME)
        if (null != bumper) {
            return bumper;
        }

        VersionBumperModel model1 = new VersionBumperModel();

        model1.className = SemverVersionBumper.class.getName();
        model1.bumperName = DEFAULT_VERSION_BUMPER_NAME
        model1.description = DEFAULT_VERSION_BUMPER_NAME
        return versionBumperRepository.saveAndFlush(model1);
    }

    public TestDataReceipt createCommitWithNoChildren(String version = '1.2.3') {
        ScmMetaDataModel scmMetaData = createScmMetaData('no-children', defaultBumper)
        def commit = createCommit(scmMetaData, RandomStringUtils.randomAlphanumeric(40).toLowerCase(), DefaultCommitVersion.parse(version))

        return new TestDataReceipt(scmMetaData, [commit])
    }

    public TestDataReceipt createCommitWithNext() {
        ScmMetaDataModel scmMetaData = createScmMetaData('next', defaultBumper)

        RepositoryCommitModel childCommit = createCommit(scmMetaData, RandomStringUtils.randomAlphanumeric(40).toLowerCase(), DefaultCommitVersion.parse('1.2.4'))
        RepositoryCommitModel parentCommit = createCommit(scmMetaData, RandomStringUtils.randomAlphanumeric(40).toLowerCase(), DefaultCommitVersion.parse('1.2.3'))

        parentCommit.setNextCommit(childCommit);
        parentCommit = commitRepository.save(parentCommit);

        return new TestDataReceipt(scmMetaData, [parentCommit, childCommit])
    }

    public TestDataReceipt createWithNextAndBuildCommits() {
        ScmMetaDataModel scmMetaData = createScmMetaData('build', defaultBumper)

        RepositoryCommitModel childCommit = createCommit(scmMetaData, RandomStringUtils.randomAlphanumeric(40).toLowerCase(), DefaultCommitVersion.parse('1.2.4'))
        RepositoryCommitModel buildCommit = createCommit(scmMetaData, RandomStringUtils.randomAlphanumeric(40).toLowerCase(), DefaultCommitVersion.parse('1.2.3.1'))
        RepositoryCommitModel parentCommit = createCommit(scmMetaData, RandomStringUtils.randomAlphanumeric(40).toLowerCase(), DefaultCommitVersion.parse('1.2.3'))

        parentCommit.setNextCommit(childCommit);
        parentCommit.setBugfixCommit(buildCommit)
        parentCommit = commitRepository.save(parentCommit);

        return new TestDataReceipt(scmMetaData, [parentCommit, childCommit, buildCommit])
    }

    private RepositoryCommitModel createCommit(ScmMetaDataModel scmMetaData, String commitId, CommitVersion version) {
        RepositoryCommitModel commit = new RepositoryCommitModel(commitId, version);
        commit.setScmMetaDataModel(scmMetaData);
        commitRepository.save(commit);
        return commit
    }

    private ScmMetaDataModel createScmMetaData(String repoId, VersionBumperModel bumper) {
        ScmMetaDataModel scmMetaData = new ScmMetaDataModel();

        scmMetaData.setUuid(UUID.randomUUID());
        scmMetaData.setRepoName(repoId);
        scmMetaData.setVersionBumperModel(bumper);
        scmMetaData = scmMetaDataRepository.save(scmMetaData);
        return scmMetaData
    }

    public static DefaultCommitDetails createNewCommit(TestDataReceipt testDataReceipt) {
        return new DefaultCommitDetails("message?",
            RandomStringUtils.randomAlphanumeric(40).toLowerCase(),
            testDataReceipt.commits.first().commitId,
            testDataReceipt.scmMetaDataModel.uuid);
    }

    @TupleConstructor
    public static class TestDataReceipt {
        ScmMetaDataModel scmMetaDataModel
        List<RepositoryCommitModel> commits;
    }


}
