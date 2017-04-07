package tech.crom.model.bumper

import tech.crom.model.commit.VersionDetails
import tech.crom.model.commit.impl.RealizedCommit
import tech.crom.model.commit.impl.RequestedCommit

data class CromVersionBumper(val bumperId: Long,
                             val bumperName: String,
                             val className: String,
                             val description: String,
                             val executor: Executor) {
    interface Executor {
        fun calculateNextVersion(commitModel: RequestedCommit, lastVersion: VersionDetails?): RealizedCommit
    }
}
