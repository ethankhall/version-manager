package tech.crom.security.authorization.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.database.api.VersionBumperManager
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.api.PermissionService
import tech.crom.security.authorization.confg.TestConfig
import tech.crom.security.authorization.testdouble.WithMockCromUser

@Transactional
@SpringBootTest(classes = [TestConfig],
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource("/application-test.yml")
class DefaultPermissionServiceTest extends Specification {

    @Autowired
    PermissionService permissionService

    @Autowired
    ProjectManager projectManager

    @Autowired
    RepoManager repoManager

    @Autowired
    VersionBumperManager versionBumperManager

    @WithMockCromUser
    def 'can create permission for project'() {
        when:
        def project = projectManager.createProject('testProject')
        permissionService.registerProject(project)

        then:
        permissionService.hasAccessTo(project, CromPermission.ADMIN)
        permissionService.hasAccessTo(project, CromPermission.READ)
        permissionService.hasAccessTo(project, CromPermission.WRITE)
    }

    @WithMockCromUser
    def 'can create permission for repo'() {
        when:
        def project = projectManager.createProject('testProject')
        permissionService.registerProject(project)

        def bumper = versionBumperManager.findBumper("semver")
        def repo = repoManager.createRepo(project, "name", bumper, "", "", true)
        permissionService.registerRepository(repo)

        then:
        permissionService.findHighestPermission(repo) == CromPermission.ADMIN
        permissionService.hasAccessTo(repo, CromPermission.ADMIN)
        permissionService.hasAccessTo(repo, CromPermission.READ)
        permissionService.hasAccessTo(repo, CromPermission.WRITE)
    }

    @WithMockCromUser
    def 'will throw if different user tries to access project'() {
        when:
        def project = projectManager.createProject('testProject')
        permissionService.registerProject(project)

        def bumper = versionBumperManager.findBumper("semver")
        def repo = repoManager.createRepo(project, "name", bumper, "", "", true)
        permissionService.registerRepository(repo)

        def authentication = new CromUserAuthentication(new CromUser(AuthUtils.randomLongGenerator(), '', ''))
        SecurityContextHolder.getContext().setAuthentication(authentication)

        then:
        !permissionService.hasAccessTo(project, CromPermission.ADMIN)
        !permissionService.hasAccessTo(project, CromPermission.READ)
        !permissionService.hasAccessTo(project, CromPermission.WRITE)

        !permissionService.hasAccessTo(repo, CromPermission.ADMIN)
        !permissionService.hasAccessTo(repo, CromPermission.READ)
        !permissionService.hasAccessTo(repo, CromPermission.WRITE)
    }

    @WithMockCromUser
    def 'can grant new permissions to user'() {
        when:
        def project = projectManager.createProject('testProject')
        permissionService.registerProject(project)

        def bumper = versionBumperManager.findBumper("semver")
        def repo = repoManager.createRepo(project, "name", bumper, "", "", true)
        permissionService.registerRepository(repo)

        def newUser = new CromUser(AuthUtils.randomLongGenerator(), '', '')

        then:
        !permissionService.hasAccessTo(newUser, project, CromPermission.ADMIN)
        !permissionService.hasAccessTo(newUser, project, CromPermission.READ)
        !permissionService.hasAccessTo(newUser, project, CromPermission.WRITE)

        !permissionService.hasAccessTo(newUser, repo, CromPermission.ADMIN)
        !permissionService.hasAccessTo(newUser, repo, CromPermission.WRITE)
        !permissionService.hasAccessTo(newUser, repo, CromPermission.READ)

        when:
        permissionService.grantPermission(newUser, repo, CromPermission.WRITE)

        then:
        !permissionService.hasAccessTo(newUser, project, CromPermission.ADMIN)
        !permissionService.hasAccessTo(newUser, project, CromPermission.READ)
        !permissionService.hasAccessTo(newUser, project, CromPermission.WRITE)

        !permissionService.hasAccessTo(newUser, repo, CromPermission.ADMIN)
        permissionService.hasAccessTo(newUser, repo, CromPermission.WRITE)
        permissionService.hasAccessTo(newUser, repo, CromPermission.READ)

        when:
        permissionService.grantPermission(newUser, project, CromPermission.READ)

        then:
        !permissionService.hasAccessTo(newUser, project, CromPermission.ADMIN)
        !permissionService.hasAccessTo(newUser, project, CromPermission.WRITE)
        permissionService.hasAccessTo(newUser, project, CromPermission.READ)

        !permissionService.hasAccessTo(newUser, repo, CromPermission.ADMIN)
        permissionService.hasAccessTo(newUser, repo, CromPermission.WRITE)
        permissionService.hasAccessTo(newUser, repo, CromPermission.READ)

        when:
        permissionService.grantPermission(newUser, project, CromPermission.ADMIN)

        then:
        permissionService.hasAccessTo(newUser, project, CromPermission.ADMIN)
        permissionService.hasAccessTo(newUser, project, CromPermission.WRITE)
        permissionService.hasAccessTo(newUser, project, CromPermission.READ)

        permissionService.hasAccessTo(newUser, repo, CromPermission.ADMIN)
        permissionService.hasAccessTo(newUser, repo, CromPermission.WRITE)
        permissionService.hasAccessTo(newUser, repo, CromPermission.READ)

        when:
        permissionService.revokePermission(newUser, project, CromPermission.ADMIN)

        then:
        !permissionService.hasAccessTo(newUser, project, CromPermission.ADMIN)
        !permissionService.hasAccessTo(newUser, project, CromPermission.WRITE)
        !permissionService.hasAccessTo(newUser, project, CromPermission.READ)

        !permissionService.hasAccessTo(newUser, repo, CromPermission.ADMIN)
        permissionService.hasAccessTo(newUser, repo, CromPermission.WRITE)
        permissionService.hasAccessTo(newUser, repo, CromPermission.READ)
    }
}
