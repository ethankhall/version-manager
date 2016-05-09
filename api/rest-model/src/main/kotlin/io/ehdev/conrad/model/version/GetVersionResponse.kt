package io.ehdev.conrad.model.version

import java.time.ZonedDateTime

class GetVersionResponse(commitId: String, version: String?, createdAt: ZonedDateTime?) : BaseVersionResponse(commitId, version, createdAt)
