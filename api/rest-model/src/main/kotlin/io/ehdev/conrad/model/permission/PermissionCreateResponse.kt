package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty

class PermissionCreateResponse(@JsonProperty("accepted") val accepted: Boolean = false)
