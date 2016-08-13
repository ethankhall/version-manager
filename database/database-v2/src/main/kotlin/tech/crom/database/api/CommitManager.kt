package tech.crom.database.api

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.repository.CromRepo

interface CommitManager {
    fun findLatestCommit(cromRepo: CromRepo, history: List<CommitIdContainer>): CommitDetails.PersistedCommit?

    fun createCommit(cromRepo: CromRepo, generatedVersion: CommitDetails.RealizedCommit, parent: List<CommitIdContainer>): CommitDetails.PersistedCommit

    fun findCommit(cromRepo: CromRepo, apiCommit: CommitIdContainer): CommitDetails.PersistedCommit?

    fun findAllCommits(cromRepo: CromRepo): List<CommitDetails.PersistedCommit>
}
