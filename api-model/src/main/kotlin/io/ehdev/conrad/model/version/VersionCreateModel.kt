package io.ehdev.conrad.model.version

import javax.validation.constraints.NotNull

class VersionCreateModel : VersionSearchModel {
    @NotNull
    var message: String

    @NotNull
    var commitId: String

    @NotNull
    var token: String

    constructor(commits: List<String>, message: String, commitId: String, token: String) : super(commits) {
        this.message = message
        this.commitId = commitId
        this.token = token
    }
}
