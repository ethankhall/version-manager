package tech.crom.version.bumper.impl.atomic

import tech.crom.model.bumper.CromVersionBumper
import tech.crom.model.commit.VersionDetails
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.commit.impl.RequestedCommit

class AtomicVersionBumper : CromVersionBumper.Executor {
    override fun calculateNextVersion(commitModel: RequestedCommit, lastVersion: VersionDetails?): RealizedCommit {
        val currentVersion = getCurrentVersion(lastVersion)
        return RealizedCommit(commitModel.commitId, VersionDetails((currentVersion + 1).toString()))
    }

    private fun getCurrentVersion(lastVersion: VersionDetails?): Int {
        lastVersion ?: return 0

        return lastVersion.versionParts.first()
    }
}
