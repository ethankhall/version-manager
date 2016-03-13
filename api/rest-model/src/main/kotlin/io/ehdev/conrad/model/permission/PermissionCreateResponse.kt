package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.DefaultResourceSupport

class PermissionCreateResponse(@JsonProperty("accepted") val accepted: Boolean = false) : DefaultResourceSupport()
