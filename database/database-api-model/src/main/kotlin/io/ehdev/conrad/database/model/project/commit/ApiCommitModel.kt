package io.ehdev.conrad.database.model.project.commit

import org.apache.commons.lang3.builder.ToStringBuilder
import java.time.ZonedDateTime

class ApiCommitModel(val commitId: String, val version: String?, val createdAt: ZonedDateTime?) {
    constructor(commitId: String) : this(commitId, null, null)

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("commitId", commitId)
            .append("version", version)
            .append("createdAt", createdAt)
            .toString()
    }
}
