package tech.crom.version.bumper

import tech.crom.version.bumper.model.CommitModel
import tech.crom.version.bumper.model.ReservedVersionModel

interface VersionBumper {
    fun calculateNextVersion(commitModel: CommitModel, lastCommit: ReservedVersionModel): ReservedVersionModel
}
