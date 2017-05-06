package tech.crom.rest.model.repository.statemachine

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.InputModel

@InputModel
data class UpdateStateMachine(
    @JsonProperty("defaultState")
    val defaultState: String,

    @JsonProperty("transitions")
    val transitions: Map<String, UpdateStateTransitions>,

    @JsonProperty("migrateCurrent")
    val migrateCurrent: Map<String, String>)

