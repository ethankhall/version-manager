package tech.crom.rest.model.version

import tech.crom.rest.model.OutputModel
import java.time.ZonedDateTime

@OutputModel
class GetVersionResponse(commitId: String, version: String?, createdAt: ZonedDateTime?) : BaseVersionResponse(commitId, version, createdAt)
