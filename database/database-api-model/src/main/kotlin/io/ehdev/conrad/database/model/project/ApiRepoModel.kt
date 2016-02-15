package io.ehdev.conrad.database.model.project

interface ApiRepoModel {
    val projectName: String?
    val repoName: String?

    fun getMergedName(): String {
        return projectName + "/" + repoName
    }
}
