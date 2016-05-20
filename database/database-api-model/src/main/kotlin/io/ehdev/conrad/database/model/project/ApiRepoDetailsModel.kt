package io.ehdev.conrad.database.model.project

class ApiRepoDetailsModel(
    val repo: ApiFullRepoModel,
    val bumper: ApiVersionBumperModel
) {
    fun getMergedName(): String {
        return repo.mergedName
    }
}
