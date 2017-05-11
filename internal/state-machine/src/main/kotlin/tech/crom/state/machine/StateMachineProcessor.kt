package tech.crom.state.machine

import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitionNotification
import tech.crom.state.machine.exception.IllegalStateTransitionException
import tech.crom.state.machine.exception.UnknownStateException

class StateMachineProcessor(val definition: StateMachineDefinition) {

    fun doTransition(current: String, nextState: String): List<StateTransitionNotification> {
        return doTransition(current, nextState, 0)
    }

    private fun doTransition(current: String, nextState: String, depth: Int): List<StateTransitionNotification> {
        val transitionQueue = mutableListOf<StateTransitionNotification>()

        val currentStateDef = definition.stateTransitions[current] ?: throw UnknownStateException(current)
        val nextStateDef = definition.stateTransitions[nextState] ?: throw UnknownStateException(nextState)

        if (!currentStateDef.nextStates.contains(nextState)) throw IllegalStateTransitionException(nextState)

        if (nextStateDef.forceTransition != null) {
            transitionQueue.addAll(doTransition(nextState, nextStateDef.forceTransition!!, depth + 1))
        }

        if (depth != 0) {
            transitionQueue.add(StateTransitionNotification(current, nextState))
        }

        return transitionQueue
    }

    fun newVersionTransitions(): List<StateTransitionNotification> {
        val transitionQueue = mutableListOf<StateTransitionNotification>()
        val defaultState = definition.stateTransitions[definition.defaultState]!!

        if (defaultState.forceTransition != null) {
            transitionQueue.addAll(doTransition(VersionCommitDetails.DEFAULT_STATE, definition.defaultState))
        }

        transitionQueue.add(StateTransitionNotification(VersionCommitDetails.DEFAULT_STATE, definition.defaultState))
        return transitionQueue
    }
}
