package tech.crom.model.commit.impl

import tech.crom.model.commit.CommitDetails
import java.time.ZonedDateTime

data class RequestedCommit(override val commitId: String,
                           val message: String,
                           val createdAt: ZonedDateTime? = null) : CommitDetails
