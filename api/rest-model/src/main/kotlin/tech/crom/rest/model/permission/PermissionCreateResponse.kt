package tech.crom.rest.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel

@OutputModel
class PermissionCreateResponse(@JsonProperty("accepted") val accepted: Boolean = false)
