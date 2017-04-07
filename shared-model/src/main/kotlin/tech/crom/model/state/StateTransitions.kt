package tech.crom.model.state

data class StateTransitions(val nextStates: List<String>, val forceTransition: String?)
