package tech.crom.state.machine

import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitionNotification
import tech.crom.state.machine.exception.IllegalStateTransitionException
import tech.crom.state.machine.exception.UnknownStateException
import tech.crom.state.machine.exception.UnknownVersionIdException

class StateMachineProcessor(val definition: StateMachineDefinition, val currentStateMachine: MutableMap<Long, String>) {

    fun doTransition(versionId: Long, nextState: String): List<StateTransitionNotification> {
        val transitionQueue = mutableListOf<StateTransitionNotification>()
        val currentState = currentStateMachine[versionId] ?: throw UnknownVersionIdException(versionId)

        val currentStateDef = definition.stateTransitions[currentState] ?: throw UnknownStateException(currentState)
        val nextStateDef = definition.stateTransitions[nextState] ?: throw UnknownStateException(nextState)

        if (!currentStateDef.nextStates.contains(nextState)) throw IllegalStateTransitionException(nextState)

        if (nextStateDef.forceTransition != null) {
            currentStateMachine
                .filterValues { it == nextState }
                .forEach { transitionQueue.addAll(doTransition(it.key, nextStateDef.forceTransition!!)) }
        }

        currentStateMachine[versionId] = nextState
        transitionQueue.add(StateTransitionNotification(versionId, currentState, nextState))

        return transitionQueue
    }

    fun addVersion(versionId: Long): List<StateTransitionNotification> {
        val transitionQueue = mutableListOf<StateTransitionNotification>()
        val nextStateDef = definition.stateTransitions[definition.defaultState] ?: throw UnknownStateException(definition.defaultState)

        if (nextStateDef.forceTransition != null) {
            currentStateMachine
                .filterValues { it == definition.defaultState }
                .forEach { transitionQueue.addAll(doTransition(it.key, nextStateDef.forceTransition!!)) }
        }

        currentStateMachine[versionId] = definition.defaultState
        transitionQueue.add(StateTransitionNotification(versionId, null, definition.defaultState))

        return transitionQueue
    }
}
