package io.ehdev.conrad.model.user

import com.fasterxml.jackson.annotation.JsonProperty

data class GetFullProfileResponse(
    @JsonProperty("userName")
    val userName: String,

    @JsonProperty("email")
    val email: String?,

    @JsonProperty("displayName")
    val displayName: String
)
