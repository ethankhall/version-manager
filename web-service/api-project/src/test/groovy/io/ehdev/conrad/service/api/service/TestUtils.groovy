package io.ehdev.conrad.service.api.service

import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.web.api.model.RequestDetails

class TestUtils {

    static RequestDetails createTestingRepoModel(CromPermission projectPermission = CromPermission.NONE,
                                                 CromPermission repoPermission = CromPermission.NONE) {
        def project = new CromProject(UUID.randomUUID(), 1, 'projectName')
        def repo = new CromRepo(UUID.randomUUID(), 2, project.projectUid, 'repoName', UUID.randomUUID())
        def user = new CromUser(UUID.randomUUID(), 'userName', 'displayName')
        def permissions = new RequestDetails.RequestPermissions(projectPermission, repoPermission, user)
        def details = new RequestDetails.RawRequestDetails("/", [:])
        return new RequestDetails(project, repo, permissions, details)
    }
}
