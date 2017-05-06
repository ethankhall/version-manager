package tech.crom.state.machine

import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitionNotification
import tech.crom.state.machine.exception.IllegalStateTransitionException
import tech.crom.state.machine.exception.UnknownStateException

class StateMachineProcessor(val definition: StateMachineDefinition) {

    fun doTransition(current: String, nextState: String): List<StateTransitionNotification> {
        val transitionQueue = mutableListOf<StateTransitionNotification>()

        val currentStateDef = definition.stateTransitions[current] ?: throw UnknownStateException(current)
        val nextStateDef = definition.stateTransitions[nextState] ?: throw UnknownStateException(nextState)

        if (!currentStateDef.nextStates.contains(nextState)) throw IllegalStateTransitionException(nextState)

        if (nextStateDef.forceTransition != null) {
            definition.stateTransitions[nextState]!!.nextStates
                .forEach { transitionQueue.addAll(doTransition(nextState, nextStateDef.forceTransition!!)) }
        }

        transitionQueue.add(StateTransitionNotification(current, nextState))

        return transitionQueue
    }
}
