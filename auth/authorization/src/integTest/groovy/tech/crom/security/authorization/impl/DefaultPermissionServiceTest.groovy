package tech.crom.security.authorization.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import tech.crom.config.ClockConfig
import tech.crom.config.DatabaseConfig
import tech.crom.database.api.ProjectManager
import tech.crom.database.api.RepoManager
import tech.crom.database.api.VersionBumperManager
import tech.crom.database.config.CromDoaConfig
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.api.PermissionService
import tech.crom.security.authorization.config.AuthorizationConfig
import tech.crom.security.authorization.testdouble.StubAuthenticationManager
import tech.crom.security.authorization.testdouble.WithMockCromUser

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@Transactional
@SpringBootTest(classes = [DatabaseConfig, ClockConfig, CromDoaConfig, AuthorizationConfig, StubAuthenticationManager], webEnvironment = NONE)
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

        SecurityContextHolder.getContext().setAuthentication(new CromUserAuthentication(new CromUser(UUID.randomUUID(), '', '')))

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

        def newUser = new CromUser(UUID.randomUUID(), '', '')

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
    }
}
