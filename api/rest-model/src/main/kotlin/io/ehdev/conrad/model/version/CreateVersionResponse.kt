package io.ehdev.conrad.model.version

import java.time.ZonedDateTime

class CreateVersionResponse(commitId: String, version: String, createdAt: ZonedDateTime) : BaseVersionResponse(commitId, version, createdAt)
