package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.model.ApiParameterContainer
import io.ehdev.conrad.db.tables.daos.VersionBumpersDao
import io.ehdev.conrad.db.tables.pojos.VersionBumpers
import io.ehdev.conrad.model.rest.RestRepoCreateModel
import io.ehdev.conrad.service.api.config.TestConradProjectApiConfiguration
import io.ehdev.conrad.version.bumper.SemanticVersionBumper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import javax.transaction.Transactional

@Rollback
@Transactional
@TestPropertySource(properties = ["api.verification = false"])
@ContextConfiguration(classes = [TestConradProjectApiConfiguration], loader = SpringApplicationContextLoader)
class RepoEndpointIntegrationTest extends Specification {

    @Autowired
    ProjectEndpoint projectEndpoint

    @Autowired
    RepoEndpoint repoEndpoint

    @Autowired
    VersionBumpersDao versionBumpersDao

    def setup() {
        projectEndpoint.createProject(new ApiParameterContainer(null, "project_name", null), new MockHttpServletRequest())
        versionBumpersDao.insert(new VersionBumpers(null, 'semver', SemanticVersionBumper.getName(), 'semver'))
    }

    def 'full workflow'() {
        when:
        def repo = repoEndpoint.createRepo(createApiContainer(), new RestRepoCreateModel("semver", 'url'))

        then:
        repo.statusCode == HttpStatus.CREATED

        and:
        when:
        repo = repoEndpoint.createRepo(createApiContainer(), new RestRepoCreateModel("semver", 'url'))

        then:
        repo.statusCode == HttpStatus.CONFLICT
    }

    private ApiParameterContainer createApiContainer() {
        new ApiParameterContainer(null, "project_name", "repo_name")
    }

}
