package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.CommitApi
import tech.crom.business.api.VersionBumperApi
import tech.crom.business.exception.CommitNotFoundException
import tech.crom.database.api.CacheManager
import tech.crom.database.api.CommitManager
import tech.crom.database.api.StateMachineManager
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.VersionDetails
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.model.repository.CromRepo
import tech.crom.state.machine.StateMachineProcessor

@Service
class DetaultCommitApi @Autowired constructor(
    val commitManager: CommitManager,
    val stateMachineManager: StateMachineManager,
    val cacheManager: CacheManager,
    val versionBumperApi: VersionBumperApi
) : CommitApi {

    override fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit> = commitManager.findAllCommits(cromRepo)

    override fun findCommit(cromRepo: CromRepo, filer: CommitFilter): PersistedCommit? {
        return commitManager.findCommit(cromRepo, filer)
    }

    override fun createCommit(cromRepo: CromRepo,
                              commitModel: RequestedCommit,
                              commitList: List<CommitIdContainer>): PersistedCommit {

        val latestCommit = commitManager.findCommit(cromRepo, CommitFilter(commitList))
        val nextVersion = versionBumperApi
            .findVersionBumper(cromRepo)
            .executor
            .calculateNextVersion(commitModel, latestCommit.toVersionDetails())
        val createCommit = commitManager.createCommit(cromRepo, nextVersion, commitList)

        val processor = StateMachineProcessor(stateMachineManager.getStateMachine(cromRepo))
        val newVersionTransitions = processor.newVersionTransitions()
        commitManager.moveVersionsInState(cromRepo, newVersionTransitions)

        return commitManager.findCommit(cromRepo, CommitIdContainer(createCommit.commitId))!!
    }

    override fun updateState(cromRepo: CromRepo, commitId: CommitIdContainer, nextState: String) {
        val commit = commitManager.findCommit(cromRepo, CommitFilter(listOf(commitId))) ?:
            throw CommitNotFoundException(commitId.commitId)

        val definition = stateMachineManager.getStateMachine(cromRepo)
        val processor = StateMachineProcessor(definition)
        val newVersionTransitions = processor.doTransition(commit.state, nextState)

        commitManager.moveVersionsInState(cromRepo, newVersionTransitions)
        commitManager.setVersionToState(commit, nextState)

        cacheManager.purgeCacheFor(cromRepo, commit)
    }

    fun PersistedCommit?.toVersionDetails(): VersionDetails? {
        if (this == null) return null else return this.version
    }
}
