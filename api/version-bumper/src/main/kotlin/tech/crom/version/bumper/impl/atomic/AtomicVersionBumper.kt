package tech.crom.version.bumper.impl.atomic

import tech.crom.version.bumper.VersionBumper
import tech.crom.version.bumper.model.CommitModel
import tech.crom.version.bumper.model.ReservedVersionModel
import tech.crom.version.bumper.model.VersionDetails
import java.time.LocalDateTime

class AtomicVersionBumper : VersionBumper {
    override fun calculateNextVersion(commitModel: CommitModel, lastCommit: ReservedVersionModel?): ReservedVersionModel {
        val currentVersion = getCurrentVersion(lastCommit)
        return ReservedVersionModel(commitModel.commitId, VersionDetails((currentVersion + 1).toString()), LocalDateTime.now())
    }

    private fun getCurrentVersion(lastCommit: ReservedVersionModel?): Int {
        lastCommit ?: return 0

        return lastCommit.version.versionParts.first()
    }
}
