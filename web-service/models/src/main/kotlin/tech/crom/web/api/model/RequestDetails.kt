package tech.crom.web.api.model

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser

data class RequestDetails(val cromProject: CromProject?,
                          val cromRepo: CromRepo?,
                          val requestPermission: RequestPermissions,
                          val rawRequest: RawRequestDetails) {

    data class RawRequestDetails(val requestPath: String, val method: String, val headers: Map<String, String>, val requestParameters: Map<String, String>) {
        fun getProjectName(): String? = requestParameters["projectName"]

        fun getRepoName(): String? = requestParameters["repoName"]
    }

}
