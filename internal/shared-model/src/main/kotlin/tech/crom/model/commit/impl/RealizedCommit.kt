package tech.crom.model.commit.impl

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.commit.VersionDetails
import java.time.ZonedDateTime

data class RealizedCommit(override val commitId: String,
                          override val version: VersionDetails,
                          override val state: String,
                          val createdAt: ZonedDateTime? = null) : CommitDetails, VersionCommitDetails {

    companion object {
        @JvmStatic
        fun createNewCommit(commitId: String, version: String, createdAt: ZonedDateTime?): RealizedCommit {
            return RealizedCommit(commitId, VersionDetails(version), VersionCommitDetails.DEFAULT_STATE, createdAt)
        }
    }
}
