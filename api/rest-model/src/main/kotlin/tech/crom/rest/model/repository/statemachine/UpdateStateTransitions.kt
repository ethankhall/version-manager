package tech.crom.rest.model.repository.statemachine

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateStateTransitions(
    @JsonProperty("nextStates")
    val nextStates: List<String>,

    @JsonProperty("autoTransition")
    val autoTransition: String?)
