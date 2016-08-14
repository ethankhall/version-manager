package tech.crom.business.api

import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.model.repository.CromRepo

interface CommitApi {
    fun createCommit(cromRepo: CromRepo,
                     commitModel: RequestedCommit,
                     commitList: List<CommitIdContainer>): PersistedCommit

    fun findCommit(cromRepo: CromRepo, commitIdContainer: CommitIdContainer): PersistedCommit?

    fun findLatestCommit(cromRepo: CromRepo, commitIdContainer: List<CommitIdContainer>): PersistedCommit?

    fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit>
}
