package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription

data class PermissionGrant(
    @JsonPropertyDescription("Name of the user to grant the permission to.")
    @JsonProperty("username")
    val username: String,

    @JsonPropertyDescription("Type of permission to grant access to")
    @JsonProperty("permission")
    val permission: PermissionDefinition) {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    enum class PermissionDefinition {
        NONE,
        READ,
        WRITE,
        ADMIN
    }

}
