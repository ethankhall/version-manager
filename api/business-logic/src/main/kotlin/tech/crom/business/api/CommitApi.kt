package tech.crom.business.api

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.repository.CromRepo

interface CommitApi {
    fun createCommit(cromRepo: CromRepo,
                     commitModel: CommitDetails.RequestedCommit,
                     commitList: List<CommitIdContainer>): CommitDetails.PersistedCommit

    fun findAllCommits(cromRepo: CromRepo): List<CommitDetails.PersistedCommit>
}
