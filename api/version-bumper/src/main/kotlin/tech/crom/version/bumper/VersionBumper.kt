package tech.crom.version.bumper

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.commit.impl.RequestedCommit
import tech.crom.model.commit.VersionDetails

interface VersionBumper {
    fun calculateNextVersion(commitModel: RequestedCommit, lastVersion: VersionDetails?): RealizedCommit
}
