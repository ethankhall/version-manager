package tech.crom.version.bumper.model

import java.time.LocalDateTime

data class ReservedVersionModel(val commitId: String, val version: VersionDetails, val createdAt: LocalDateTime)
