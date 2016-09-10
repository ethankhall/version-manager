package io.ehdev.conrad.model.user

import com.fasterxml.jackson.annotation.JsonProperty

data class PostUserUpdate(@JsonProperty("updates") val watches: List<Details>) {
    data class Details(@JsonProperty("field") val field: FieldDetails,
                       @JsonProperty("value") val value: String)
    enum class FieldDetails {
        USER_NAME,
        DISPLAY_NAME
    }
}
