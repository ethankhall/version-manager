package tech.crom.model.commit

import java.time.ZonedDateTime
import java.util.*

interface CommitDetails {
    val commitId: String

    data class RequestedCommit(override val commitId: String,
                               val message: String,
                               val createdAt: ZonedDateTime? = null) : CommitDetails

    data class RealizedCommit(override val commitId: String,
                              val version: VersionDetails,
                              val createdAt: ZonedDateTime? = null) : CommitDetails {
        constructor(commitId: String,
                    version: String,
                    createdAt: ZonedDateTime?): this(commitId, VersionDetails(version), createdAt)
    }

    data class PersistedCommit(val commitUid: UUID,
                               override val commitId: String,
                               val version: VersionDetails,
                               val createdAt: ZonedDateTime) : CommitDetails {
        constructor(commitUid: UUID,
                    commitId: String,
                    version: String,
                    createdAt: ZonedDateTime): this(commitUid, commitId, VersionDetails(version), createdAt)
    }

}
