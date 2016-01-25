package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import javax.validation.constraints.NotNull
import kotlin.collections.first

class RepoVersionCollectionModel {

    @NotNull
    @JsonView(VersionView.ReleasedVersionView::class)
    var commits: List<VersionCommitModel>

    @JsonView(VersionView.ReleasedVersionView::class)
    var latest: VersionCommitModel

    @JsonCreator
    constructor(@JsonProperty("commits") commits: List<VersionCommitModel>) {
        this.commits = commits
        latest = commits.first()
    }
}
