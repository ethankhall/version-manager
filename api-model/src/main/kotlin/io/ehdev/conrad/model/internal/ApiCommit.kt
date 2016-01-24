package io.ehdev.conrad.model.internal

class ApiCommit(val commitId: String, val version: String? = null, val parentId: String? = null) {
    constructor(commitId: String) : this(commitId, null, null)
}

