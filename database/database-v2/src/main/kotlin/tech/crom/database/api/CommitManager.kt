package tech.crom.database.api

import tech.crom.model.commit.CommitFilter
import tech.crom.model.commit.CommitIdContainer
import tech.crom.model.commit.impl.PersistedCommit
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateTransitionNotification

interface CommitManager {
    fun findCommit(cromRepo: CromRepo, filer: CommitFilter): PersistedCommit?

    fun createCommit(cromRepo: CromRepo, generatedVersion: RealizedCommit, parent: List<CommitIdContainer>): PersistedCommit

    fun findCommit(cromRepo: CromRepo, apiCommit: CommitIdContainer): PersistedCommit?

    fun findAllCommits(cromRepo: CromRepo): List<PersistedCommit>

    fun moveVersionsInState(cromRepo: CromRepo, transitions: List<StateTransitionNotification>)
}
