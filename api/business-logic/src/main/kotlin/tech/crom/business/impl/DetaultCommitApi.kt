package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.CommitApi
import tech.crom.business.api.VersionBumperApi
import tech.crom.database.api.CommitManager
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.VersionDetails
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.model.repository.CromRepo

@Service
class DetaultCommitApi @Autowired constructor(
    val commitManager: CommitManager,
    val versionBumperApi: VersionBumperApi
): CommitApi {
    override fun findCommit(cromRepo: CromRepo, commitIdContainer: CommitIdContainer): PersistedCommit? {
        return commitManager.findCommit(cromRepo, commitIdContainer)
    }

    override fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit> = commitManager.findAllCommits(cromRepo)

    override fun createCommit(cromRepo: CromRepo,
                              commitModel: RequestedCommit,
                              commitList: List<CommitIdContainer>): PersistedCommit {

        val latestCommit = commitManager.findLatestCommit(cromRepo, commitList)
        val nextVersion = versionBumperApi
            .findVersionBumper(cromRepo)
            .executor
            .calculateNextVersion(commitModel, latestCommit.toVersionDetails())
        return commitManager.createCommit(cromRepo, nextVersion, commitList)
    }

    fun PersistedCommit?.toVersionDetails(): VersionDetails? {
        if (this == null) return null else return this.version
    }
}
