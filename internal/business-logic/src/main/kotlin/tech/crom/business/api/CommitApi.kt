package tech.crom.business.api

import tech.crom.business.exception.CommitNotFoundException
import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.model.repository.CromRepo

interface CommitApi {
    fun createCommit(cromRepo: CromRepo,
                     commitModel: RequestedCommit,
                     commitList: List<CommitIdContainer>): PersistedCommit

    fun findCommit(cromRepo: CromRepo, filer: CommitFilter): PersistedCommit?

    fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit>

    @Throws(CommitNotFoundException::class)
    fun updateState(cromRepo: CromRepo, commitId: CommitIdContainer, nextState: String)
}
