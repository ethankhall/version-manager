package tech.crom.version.bumper.impl.semver

import tech.crom.version.bumper.VersionBumper
import tech.crom.version.bumper.model.CommitModel
import tech.crom.version.bumper.model.ReservedVersionModel

class SemanticVersionBumper: VersionBumper {
    override fun calculateNextVersion(commitModel: CommitModel, lastCommit: ReservedVersionModel?): ReservedVersionModel {
        throw RuntimeException()
    }
}
