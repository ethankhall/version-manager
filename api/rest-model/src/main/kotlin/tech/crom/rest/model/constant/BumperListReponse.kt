package tech.crom.rest.model.constant

import com.fasterxml.jackson.annotation.JsonProperty

data class BumperListReponse(@JsonProperty("bumpers") val bumpers: List<Bumper>) {
    data class Bumper(@JsonProperty("name") val name: String, @JsonProperty("description") val description: String)
}
