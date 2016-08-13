package tech.crom.version.bumper

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionDetails

interface VersionBumper {
    fun calculateNextVersion(commitModel: CommitDetails.RequestedCommit, lastVersion: VersionDetails?): CommitDetails.RealizedCommit
}
