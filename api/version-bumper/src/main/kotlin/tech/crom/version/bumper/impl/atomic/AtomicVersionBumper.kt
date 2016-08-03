package tech.crom.version.bumper.impl.atomic

import tech.crom.version.bumper.VersionBumper
import tech.crom.version.bumper.model.CommitModel
import tech.crom.version.bumper.model.ReservedVersionModel
import java.time.LocalDateTime

class AtomicVersionBumper : VersionBumper {
    override fun calculateNextVersion(commitModel: CommitModel, lastCommit: ReservedVersionModel?): ReservedVersionModel {
        val currentVersion = getCurrentVersion(lastCommit)
        return ReservedVersionModel(commitModel.commitId, (currentVersion + 1).toString(), LocalDateTime.now())
    }

    private fun getCurrentVersion(lastCommit: ReservedVersionModel?): Int {
        lastCommit ?: return 0

        if (lastCommit.version.isNullOrBlank()) {
            return 0
        }

        try {
            return lastCommit.version.toDouble().toInt()
        } catch (e: NumberFormatException) {
            return lastCommit.version.split(".").first().toInt()
        }
    }
}
