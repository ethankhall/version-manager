package tech.crom.rest.model.user

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import tech.crom.rest.model.InputModel

@InputModel
@JsonClassDescription("Change a user's details.")
data class PostUserUpdate(
    @JsonProperty("updates")
    @JsonPropertyDescription("List of updates to apply to the user.")
    val updates: List<Details>) {

    @JsonClassDescription("Description of what should be changed on the user.")
    data class Details(@JsonProperty("field") val field: FieldDetails,
                       @JsonProperty("value") val value: String)
    enum class FieldDetails {
        USER_NAME,
        DISPLAY_NAME
    }
}
