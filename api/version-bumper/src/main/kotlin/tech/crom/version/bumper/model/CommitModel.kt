package tech.crom.version.bumper.model

import java.time.LocalDateTime

data class CommitModel(val commitId: String, val message: String, val createdAt: LocalDateTime = LocalDateTime.now())
