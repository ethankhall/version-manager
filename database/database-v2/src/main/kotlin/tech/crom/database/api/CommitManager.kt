package tech.crom.database.api

import tech.crom.model.commit.CromCommitDetails
import tech.crom.model.repository.CromRepo
import java.time.Instant

interface CommitManager {
    fun findLatestCommit(cromRepo: CromRepo, history: List<CommitSearch>): CromCommitDetails?

    fun createCommit(cromRepo: CromRepo, nextVersion: NextCommitVersion, parent: List<CommitSearch>): CromCommitDetails

    fun findCommit(cromRepo: CromRepo, apiCommit: CommitSearch): CromCommitDetails?

    fun findAllCommits(cromRepo: CromRepo): List<CromCommitDetails>

    data class CommitSearch(val commitId: String)
    data class NextCommitVersion(val commitId: String, val version: String, val createdAt: Instant? = null)
}
