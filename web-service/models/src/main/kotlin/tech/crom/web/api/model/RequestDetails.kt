package tech.crom.web.api.model

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission

data class RequestDetails(val cromProject: CromProject?,
                          val cromRepo: CromRepo?,
                          val requestPermission: RequestPermissions,
                          val rawRequest: RawRequestDetails) {
    data class RawRequestDetails(val requestPath: String, val requestParameters: Map<String, String>) {
        fun getProjectName(): String? = requestParameters["projectName"]

        fun getRepoName(): String? = requestParameters["repoName"]
    }

    data class RequestPermissions(val projectPermission: CromPermission?, val repoPermission: CromPermission?) {
        fun findHighestPermission(): CromPermission {
            if(projectPermission == null) {
                return CromPermission.NONE
            }

            if(repoPermission != null) {
                if(repoPermission.isHigherOrEqualThan(projectPermission)) repoPermission else projectPermission
            }

            return projectPermission
        }
    }
}
