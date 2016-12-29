package tech.crom.rest.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel

@OutputModel
data class GetFullProfileResponse(
    @JsonProperty("userName")
    val userName: String,

    @JsonProperty("email")
    val email: String?,

    @JsonProperty("displayName")
    val displayName: String
)
