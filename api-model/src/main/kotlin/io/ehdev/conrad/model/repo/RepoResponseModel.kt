package io.ehdev.conrad.model.repo

import com.fasterxml.jackson.annotation.JsonView

class RepoResponseModel : RepoCreateModel {

    @JsonView(RepoView.Public::class)
    var id: String;

    @JsonView(RepoView.Private::class)
    var token: String;

    constructor(name: String, url: String?, bumper: String, id: String, token: String) : super(name, url, bumper) {
        this.id = id
        this.token = token
    }
}
