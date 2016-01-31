package io.ehdev.conrad.database.model.project.commit

import org.apache.commons.lang3.builder.ToStringBuilder

class ApiCommitModel(val commitId: String, val version: String?) {
    constructor(commitId: String) : this(commitId, null)

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("commitId", commitId)
            .append("version", version)
            .toString()
    }
}
