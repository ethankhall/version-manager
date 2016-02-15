package io.ehdev.conrad.database.model.project

class ApiProjectModel(
    val name: String,
    val repos: List<DefaultApiRepoModel>? = null
)
