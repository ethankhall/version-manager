package tech.crom.model.commit

import java.time.ZonedDateTime
import java.util.*

data class CromCommitDetails(val commitUid: UUID, val commitId: String, val version: String, val createdAt: ZonedDateTime)
