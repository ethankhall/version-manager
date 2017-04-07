package tech.crom.model.state

data class StateMachineDefinition(val defaultState: String, val stateTransitions: Map<String, StateTransitions>)

