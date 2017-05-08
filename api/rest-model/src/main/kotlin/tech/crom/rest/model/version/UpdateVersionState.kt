package tech.crom.rest.model.version

import com.fasterxml.jackson.annotation.JsonProperty
import tech.crom.rest.model.OutputModel

@OutputModel
data class UpdateVersionState(@JsonProperty("nextState") val nextState: String)
