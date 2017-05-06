package tech.crom.rest.model.repository.statemachine

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateStateTransitions(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("autoTransition")
    val autoTransition: String?)
