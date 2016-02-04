package io.ehdev.conrad.database.model.project

class ApiRepoDetailsModel(
    val repo: ApiRepoModel,
    val bumper: ApiVersionBumperModel
) {
    fun getMergedName(): String {
        return repo.getMergedName()
    }
}
