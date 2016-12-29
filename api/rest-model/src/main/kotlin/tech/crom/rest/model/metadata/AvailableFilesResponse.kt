package tech.crom.rest.model.metadata

import tech.crom.rest.model.OutputModel

@OutputModel
data class AvailableFilesResponse(val files: List<String>)
