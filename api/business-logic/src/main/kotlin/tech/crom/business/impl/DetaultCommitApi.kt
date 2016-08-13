package tech.crom.business.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.business.api.CommitApi
import tech.crom.business.api.VersionBumperApi
import tech.crom.database.api.CommitManager
import tech.crom.model.commit.*
import tech.crom.model.repository.CromRepo

@Service
class DetaultCommitApi @Autowired constructor(
    val commitManager: CommitManager,
    val versionBumperApi: VersionBumperApi
): CommitApi {

    override fun findAllCommits(cromRepo: CromRepo): List<CommitDetails.PersistedCommit> = commitManager.findAllCommits(cromRepo)

    override fun createCommit(cromRepo: CromRepo,
                              commitModel: CommitDetails.RequestedCommit,
                              commitList: List<CommitIdContainer>): CommitDetails.PersistedCommit {

        val latestCommit = commitManager.findLatestCommit(cromRepo, commitList)
        val nextVersion = versionBumperApi
            .findVersionBumper(cromRepo)
            .calculateNextVersion(commitModel, latestCommit.toVersionDetails())
        return commitManager.createCommit(cromRepo, nextVersion, commitList)
    }

    fun CommitDetails.PersistedCommit?.toVersionDetails(): VersionDetails? {
        if (this == null) return null else return this.version
    }
}
