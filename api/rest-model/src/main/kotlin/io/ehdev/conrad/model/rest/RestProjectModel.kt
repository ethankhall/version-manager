package io.ehdev.conrad.model.rest

class RestProjectModel(
    val name: String,
    val repos: List<RestRepoModel>? = null
)
