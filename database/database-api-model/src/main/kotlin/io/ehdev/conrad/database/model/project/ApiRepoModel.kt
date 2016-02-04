package io.ehdev.conrad.database.model.project

class ApiRepoModel(
    val projectName: String,
    val repoName: String,
    val url: String?
) {
    constructor(projectName: String, repoName: String) : this(projectName, repoName, null)

    fun getMergedName(): String {
        return projectName + "/" + repoName
    }
}
