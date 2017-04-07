package io.ehdev.conrad.service.api.service

import groovy.transform.CompileStatic
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.impl.AuthUtils
import tech.crom.web.api.model.RequestDetails
import tech.crom.web.api.model.RequestPermissions

@CompileStatic
class TestUtils {

    static RequestDetails createTestingRepoModel(CromPermission projectPermission = CromPermission.NONE,
                                                 CromPermission repoPermission = CromPermission.NONE) {
        def project = new CromProject(AuthUtils.randomLongGenerator(), 1, 'projectName')
        def repo = new CromRepo(AuthUtils.randomLongGenerator(), 2, project.projectId, 'repoName', AuthUtils.randomLongGenerator())
        def user = new CromUser(AuthUtils.randomLongGenerator(), 'userName', 'displayName')
        def permissions = new RequestPermissions(projectPermission, repoPermission, user)
        return new RequestDetails(project, repo, permissions, emptyDetails())
    }

    public static RequestDetails.RawRequestDetails emptyDetails() {
        return new RequestDetails.RawRequestDetails('/', 'POST', [:], [:])
    }
}
