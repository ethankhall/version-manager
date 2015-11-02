package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView

class VersionCommitModel : UncommitedVersionModel {

    @JsonView(VersionView.ReleasedVersionView::class)
    var commit: String;

    @JsonCreator
    constructor(@JsonProperty("commit") commit: String,
                @JsonProperty("version") version: String,
                @JsonProperty("group") group: List<Int>,
                @JsonProperty("postfix") postfix: String?) : super(version, group, postfix) {
        this.commit = commit
    }
}
