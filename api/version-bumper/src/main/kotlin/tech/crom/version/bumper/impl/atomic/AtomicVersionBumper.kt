package tech.crom.version.bumper.impl.atomic

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionDetails
import tech.crom.version.bumper.VersionBumper

class AtomicVersionBumper : VersionBumper {
    override fun calculateNextVersion(commitModel: CommitDetails.RequestedCommit, lastVersion: VersionDetails?): CommitDetails.RealizedCommit {
        val currentVersion = getCurrentVersion(lastVersion)
        return CommitDetails.RealizedCommit(commitModel.commitId, VersionDetails((currentVersion + 1).toString()))
    }

    private fun getCurrentVersion(lastVersion: VersionDetails?): Int {
        lastVersion ?: return 0

        return lastVersion.versionParts.first()
    }
}
