package io.ehdev.conrad.model.repository

import io.ehdev.conrad.model.ModelTestConfiguration

import io.ehdev.conrad.model.commit.model.RepositoryCommitModel
import io.ehdev.conrad.model.commit.model.ScmMetaDataModel
import io.ehdev.conrad.model.commit.model.VersionBumperModel
import io.ehdev.conrad.model.update.semver.SemverVersionBumper
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@Rollback(true)
@ContextConfiguration(classes = [ModelTestConfiguration.class], loader = SpringApplicationContextLoader.class)
class CommitModelRepositoryTest extends Specification {

    @Autowired
    ScmMetaDataRepository scmMetaDataRepository

    @Autowired
    VersionBumperRepository versionBumperRepository

    @Autowired
    CommitModelRepository commitModelRepository

    ScmMetaDataModel scmMetaDataModel;

    def setup() {
        def model = new ScmMetaDataModel()
        model.setRepoName('some-name')
        model.setUuid(UUID.randomUUID())

        def bumperModel = new VersionBumperModel()
        bumperModel.setClassName(SemverVersionBumper.getName())
        bumperModel.setDescription('asfasf')
        bumperModel.setBumperName('semver')

        model.setVersionBumperModel(bumperModel)
        scmMetaDataModel = scmMetaDataRepository.save(model)
    }

    def 'test getting commits'() {
        setup:
        def uuids = (0..10).collect { RandomStringUtils.randomAlphanumeric(40) }
        def version = DefaultCommitVersion.parse('0.1.2')
        uuids[5..9].each {
            version = version.bumpMinor();
            def model = new RepositoryCommitModel(it, version)
            model.setScmMetaDataModel(scmMetaDataModel)
            commitModelRepository.save(model)
        }

        when:
        def commits = commitModelRepository.findCommits(scmMetaDataModel, uuids)

        then:
        Collections.sort(commits)
        commits.size() == 5
        commits[0].commitId == uuids[5]
        commits[0].getVersion() == DefaultCommitVersion.parse('0.2.0')

    }
}
