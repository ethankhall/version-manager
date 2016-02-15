package io.ehdev.conrad.database.model.project

class DefaultApiRepoModel(
    override val projectName: String,
    override val repoName: String,
    override val url: String?
) : ApiFullRepoModel  {
    constructor(projectName: String, repoName: String) : this(projectName, repoName, null)
}
