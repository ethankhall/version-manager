package tech.crom.model.commit.impl

import tech.crom.model.commit.CommitDetails
import tech.crom.model.commit.VersionCommitDetails
import tech.crom.model.commit.VersionDetails
import java.time.ZonedDateTime

data class RealizedCommit(override val commitId: String,
                          override val version: VersionDetails,
                          val createdAt: ZonedDateTime? = null) : CommitDetails, VersionCommitDetails {
    constructor(commitId: String,
                version: String,
                createdAt: ZonedDateTime?): this(commitId, VersionDetails(version), createdAt)
}
