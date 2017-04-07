package tech.crom.database.api

import tech.crom.model.commit.VersionDetails
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateMachineDefinition

interface VersionStateManager {
    fun addVersion(repo: CromRepo, versionDetails: VersionDetails)
    fun transitionVersion(repo: CromRepo, versionDetails: VersionDetails, into: String)
    fun getStateMachine(repo: CromRepo): StateMachineDefinition
    fun updateStateMachine(repo: CromRepo, stateMachineDefinition: StateMachineDefinition)
}
