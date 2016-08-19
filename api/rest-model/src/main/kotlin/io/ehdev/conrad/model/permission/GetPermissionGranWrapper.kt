package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription

data class GetPermissionGranWrapper(
    @JsonPropertyDescription("A list containing all the permissions.")
    @JsonProperty("permissions")
    val permissions: List<PermissionGrant>
)
