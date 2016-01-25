package io.ehdev.conrad.model.version

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView

open class UncommitedVersionModel {

    @JsonView(VersionView.UnreleasedVersionView::class)
    var version: String;

    @JsonView(VersionView.UnreleasedVersionView::class)
    var group: List<Int>;

    @JsonView(VersionView.UnreleasedVersionView::class)
    var postfix: String?;

    @JsonCreator
    constructor(@JsonProperty("version") version: String,
                @JsonProperty("group") group: List<Int>,
                @JsonProperty("postfix") postfix: String?) {
        this.version = version
        this.group = group
        this.postfix = postfix
    }
}
