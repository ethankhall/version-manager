package tech.crom.state.machine.exception

class IllegalStateTransitionException(illegaleState: String): RuntimeException("Illegal next state $illegaleState")
