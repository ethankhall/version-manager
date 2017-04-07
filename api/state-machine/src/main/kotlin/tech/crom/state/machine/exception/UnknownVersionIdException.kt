package tech.crom.state.machine.exception

class UnknownVersionIdException(versionId: Long): RuntimeException("Unknown versionId $versionId")
