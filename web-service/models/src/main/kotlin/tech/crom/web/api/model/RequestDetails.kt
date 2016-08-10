package tech.crom.web.api.model

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo

data class RequestDetails(val cromProject: CromProject, val cromRepo: CromRepo)
