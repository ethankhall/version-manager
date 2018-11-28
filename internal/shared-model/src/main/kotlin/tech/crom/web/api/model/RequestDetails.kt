package tech.crom.web.api.model

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo

data class RequestDetails(val cromProject: CromProject?,
                          val cromRepo: CromRepo?,
                          val requestPermission: RequestPermissions,
                          val rawRequest: RawRequestDetails) {

    data class RawRequestDetails(val requestParameters: Map<String, String>) {
        fun getProjectName(): String? = requestParameters["projectName"]

        fun getRepoName(): String? = requestParameters["repoName"]
    }

}
