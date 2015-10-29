package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonView
import javax.validation.constraints.NotNull

class RepoVersionModel {

    @NotNull
    @JsonView(VersionView.ReleasedVersionView::class)
    var commits: List<VersionCommitModel>

    @JsonView(VersionView.ReleasedVersionView::class)
    var latest: VersionCommitModel? = null

    constructor(commits: List<VersionCommitModel>) {
        this.commits = commits
        if (!commits.isEmpty()) {
            latest = commits.last()
        }
    }
}
