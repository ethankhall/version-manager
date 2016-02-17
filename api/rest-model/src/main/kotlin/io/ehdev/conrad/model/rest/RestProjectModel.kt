package io.ehdev.conrad.model.rest

import io.ehdev.conrad.model.rest.repo.RestRepoModel

class RestProjectModel(
    val name: String,
    val repos: List<RestRepoModel>? = null
)
