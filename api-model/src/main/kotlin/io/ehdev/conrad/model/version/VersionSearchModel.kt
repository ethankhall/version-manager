package io.ehdev.conrad.model.version

import javax.validation.constraints.NotNull

open class VersionSearchModel {

    constructor(commits: List<String>) {
        this.commits = commits
    }

    @NotNull
    var commits: List<String>;
}
