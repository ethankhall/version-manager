package io.ehdev.conrad.database.model.repo

import io.ehdev.conrad.database.model.repo.details.ResourceDetails

class RepoCreateModel(val resourceDetails: ResourceDetails,
                      val bumperName: String,
                      val description: String?,
                      val url: String?,
                      val isPublic: Boolean)
