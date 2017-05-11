package tech.crom.rest.model.permission

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.annotation.JsonTypeName
import tech.crom.rest.model.InputModel

@InputModel
@JsonClassDescription("Grant accessLevel to a resource.")
@JsonTypeName("PermissionGrant")
data class PermissionGrant(
    @JsonPropertyDescription("Name of the user to grant the accessLevel to.")
    @JsonProperty("username")
    val username: String,

    @JsonPropertyDescription("Type of permission to grant access to")
    @JsonProperty("accessLevel")
    val accessLevel: AccessLevel) {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    enum class AccessLevel {
        NONE,
        READ,
        WRITE,
        ADMIN
    }

}
