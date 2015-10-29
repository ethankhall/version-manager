package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonView

class VersionCommitModel {

    @JsonView(VersionView.ReleasedVersionView::class)
    var commit: String;

    @JsonView(VersionView.UnreleasedVersionView::class)
    var version: String;

    @JsonView(VersionView.UnreleasedVersionView::class)
    var group: List<Int>;

    @JsonView(VersionView.UnreleasedVersionView::class)
    var postfix: String?;

    constructor(commit: String, version: String, group: List<Int>, postfix: String?) {
        this.commit = commit
        this.version = version
        this.group = group
        this.postfix = postfix
    }
}
