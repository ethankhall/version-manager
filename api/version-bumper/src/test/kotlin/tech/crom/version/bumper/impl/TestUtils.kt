package tech.crom.version.bumper.impl

import tech.crom.version.bumper.model.ReservedVersionModel
import tech.crom.version.bumper.model.VersionDetails
import java.time.LocalDateTime

fun createReservedVersionModel(version: String = "1.2.3", commitId: String = "123abc"): ReservedVersionModel {
    return ReservedVersionModel(commitId, VersionDetails(version), LocalDateTime.now())
}
