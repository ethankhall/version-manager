package tech.crom.model.commit

import java.time.LocalDateTime
import java.util.*

data class CromCommitDetails(val commitUid: UUID, val commitId: String, val version: String, val createdAt: LocalDateTime)
