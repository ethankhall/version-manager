package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.crom.config.ClockConfig
import tech.crom.config.DatabaseConfig
import tech.crom.database.api.*
import tech.crom.database.config.CromDoaConfig
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.metadata.StorageData

@Transactional
@ContextConfiguration(classes = [DatabaseConfig, ClockConfig, CromDoaConfig], loader = SpringApplicationContextLoader.class)
class DefaultMetaDataManagerTest extends Specification {

    @Autowired
    DSLContext dslContext

    @Autowired
    MetaDataManager metaDataManager

    @Autowired
    ProjectManager projectManager

    @Autowired
    RepoManager repoManager

    @Autowired
    CommitManager commitManager

    @Autowired
    VersionBumperManager versionBumperManager

    def 'will count the right size of bytes'() {
        def bumper = versionBumperManager.findBumper('semver')
        def project1 = projectManager.createProject("project1")
        def repo1 = repoManager.createRepo(project1, "repo1", bumper, '', '', true)
        def repo2 = repoManager.createRepo(project1, "repo2", bumper, '', '', true)

        expect:
        metaDataManager.getCurrentSizeOfProject(repo1) == 0

        when:
        def repo1Commit1 = commitManager.createCommit(repo1, new RealizedCommit('1', '1.0.0', null), [])
        metaDataManager.insertFile(repo1, repo1Commit1, new URI("gs://foo/bar/baz"), new StorageData('filename', 'foo'.bytes, ''))

        then:
        metaDataManager.getCurrentSizeOfProject(repo1) == 3
        metaDataManager.getCurrentSizeOfProject(project1) == 3

        when:
        def repo1Commit2 = commitManager.createCommit(repo1, new RealizedCommit('2', '1.0.2', null), [new CommitIdContainer('1')])
        metaDataManager.insertFile(repo1, repo1Commit2, new URI("gs://foo/bar/baz"), new StorageData('filename', 'foo'.bytes, ''))

        then:
        metaDataManager.getCurrentSizeOfProject(repo1) == 6
        metaDataManager.getCurrentSizeOfProject(project1) == 6

        when:
        def repo2Commit1 = commitManager.createCommit(repo2, new RealizedCommit('1', '1.0.0', null), [])
        metaDataManager.insertFile(repo2, repo2Commit1, new URI("gs://foo/bar/baz"), new StorageData('filename', 'foo'.bytes, ''))

        then:
        metaDataManager.getCurrentSizeOfProject(repo2) == 9
        metaDataManager.getCurrentSizeOfProject(project1) == 9

        when:
        def repo2Commit2 = commitManager.createCommit(repo2, new RealizedCommit('2', '1.0.2', null), [new CommitIdContainer('1')])
        metaDataManager.insertFile(repo2, repo2Commit2, new URI("gs://foo/bar/baz"), new StorageData('filename', 'foo'.bytes, ''))

        then:
        metaDataManager.getCurrentSizeOfProject(repo1) == 12
        metaDataManager.getCurrentSizeOfProject(project1) == 12

        when:
        metaDataManager.insertFile(repo2, repo2Commit2, new URI("gs://foo/bar/baz"), new StorageData('filename', 'foo123'.bytes, ''))

        then:
        noExceptionThrown()
        then:
        metaDataManager.getCurrentSizeOfProject(repo1) == 15
        metaDataManager.getCurrentSizeOfProject(project1) == 15

        when:
        metaDataManager.insertFile(repo1, repo1Commit1, new URI("gs://foo/bar/baz"), new StorageData('filename1', 'foo'.bytes, ''))

        then:
        metaDataManager.listFiles(repo1Commit1) == ['filename', 'filename1']
        metaDataManager.findFile(repo1Commit1, 'filename').uri.toString() == 'gs://foo/bar/baz'
    }
}
