package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty

data class PermissionGrant(
    @JsonProperty("username")
    val username: String,

    @JsonProperty("permission")
    val permission: String)
