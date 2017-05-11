package tech.crom.state.machine.exception

class UnknownStateException(state: String) : RuntimeException("Unknown state $state")
