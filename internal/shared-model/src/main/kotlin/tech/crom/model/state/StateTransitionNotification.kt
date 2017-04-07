package tech.crom.model.state

data class StateTransitionNotification(val versionId: Long, val source: String?, val target: String)
