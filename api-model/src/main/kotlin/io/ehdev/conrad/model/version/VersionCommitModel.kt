package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonView

class VersionCommitModel : UncommitedVersionModel {

    @JsonView(VersionView.ReleasedVersionView::class)
    var commit: String;

    constructor(commit: String, version: String, group: List<Int>, postfix: String?) : super(version, group, postfix) {
        this.commit = commit
    }
}
