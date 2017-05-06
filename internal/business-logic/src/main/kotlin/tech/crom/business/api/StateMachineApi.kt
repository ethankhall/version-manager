package tech.crom.business.api

import tech.crom.model.commit.VersionDetails
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateTransitions

interface StateMachineApi {
    fun getStateMachine(repo: CromRepo): StateTransitions

    fun updateStateMachine(repo: CromRepo, transitions: StateTransitions)

    fun transitionVersion(repo: CromRepo, versionDetails: VersionDetails, nextState: String)
}
