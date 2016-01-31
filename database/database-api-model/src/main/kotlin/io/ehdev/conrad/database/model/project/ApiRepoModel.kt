package io.ehdev.conrad.database.model.project

class ApiRepoModel(
    val projectName: String,
    val repoName: String,
    val url: String?
) {
    constructor(repoName: String, projectName: String) : this(repoName, projectName, null)
}
