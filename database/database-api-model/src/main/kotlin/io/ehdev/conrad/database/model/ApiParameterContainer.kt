package io.ehdev.conrad.database.model

import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.user.ApiUser

class ApiParameterContainer(val user: ApiUser?,
                            override val projectName: String?,
                            override val repoName: String?) : ApiRepoModel {

    constructor(user: ApiUser?, repo: ApiRepoModel) : this(user, repo.projectName, repo.projectName)

//    constructor() : this(null, null, null)
}
