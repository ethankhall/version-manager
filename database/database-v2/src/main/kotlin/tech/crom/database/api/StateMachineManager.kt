package tech.crom.database.api

import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateMachineDefinition

interface StateMachineManager {
    fun getStateMachine(repo: CromRepo): StateMachineDefinition

    fun updateStateMachine(repo: CromRepo, stateMachine: StateMachineDefinition)

    fun registerNewStateMachine(repo: CromRepo)
}
