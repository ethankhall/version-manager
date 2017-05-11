package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.crom.business.api.CommitApi
import tech.crom.business.api.RepositoryApi
import tech.crom.database.api.CommitManager
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.VersionBumperManager
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitions

@Transactional
@TestPropertySource("/application-test.yml")
@SpringBootTest(classes = [CommitApiConfiguration],
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DefaultCommitApiTest extends Specification {

    @Autowired
    ProjectManager projectManager

    @Autowired
    RepositoryApi repositoryApi

    @Autowired
    CommitManager commitManager

    @Autowired
    VersionBumperManager versionBumperManager

    @Autowired
    CommitApi commitApi

    def 'can find version by state'() {
        def bumper = versionBumperManager.findBumper('semver')
        def project1 = projectManager.createProject("project1")
        def repo1 = repositoryApi.createRepo(project1, "repo1", bumper, '', '', true)
        repositoryApi.updateVersionStateMachine(repo1, createStateMachineDefinition(), ['DEFAULT': 'PRE-RELEASE'])

        when:
        def commit1 = commitApi.createCommit(repo1, new RequestedCommit("abc", "some message", null), [])

        then:
        commit1.state == 'PRE-RELEASE'

        when:
        def commit2 = commitApi.createCommit(repo1, new RequestedCommit("bcd", "some message", null),
            [new CommitIdContainer('abc')])

        then:
        commit2.state == 'PRE-RELEASE'

        when:
        commitApi.updateState(repo1, new CommitIdContainer(commit2.commitId), 'RELEASED')
        commit2 = commitApi.findCommit(repo1, new CommitFilter([new CommitIdContainer(commit2.commitId)]))

        then:
        commit2.state == 'RELEASED'

        when:
        def filtered = commitApi.findCommit(repo1, new CommitFilter([new CommitIdContainer("latest")], "PRE-RELEASE"))

        then:
        filtered.version.getVersionString() == '0.0.1'
        filtered.commitId == 'abc'

        when:
        def latest = commitApi.findCommit(repo1, new CommitFilter([new CommitIdContainer("latest")]))

        then:
        latest.version.getVersionString() == '0.0.2'
        latest.commitId == 'bcd'

    }

    StateMachineDefinition createStateMachineDefinition() {
        def transitions = [
            'PRE-RELEASE': new StateTransitions(['RELEASED', "EOL"], null),
            'RELEASED'   : new StateTransitions(["EOL"], null),
            'EOL'        : new StateTransitions([], null),
        ]
        return new StateMachineDefinition("PRE-RELEASE", transitions)
    }
}
