package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.impl.bumper.VersionBumperModel
import io.ehdev.conrad.database.impl.bumper.VersionBumperModelRepository
import io.ehdev.conrad.model.rest.RestRepoCreateModel
import io.ehdev.conrad.service.api.config.TestConradProjectApiConfiguration
import io.ehdev.conrad.version.bumper.SemanticVersionBumper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@ContextConfiguration(classes = [TestConradProjectApiConfiguration], loader = SpringApplicationContextLoader)
class RepoEndpointIntegrationTest extends Specification {

    @Autowired
    ProjectEndpoint projectEndpoint

    @Autowired
    RepoEndpoint repoEndpoint

    @Autowired
    VersionBumperModelRepository bumperModelRepository

    def setup() {
        projectEndpoint.createProject("project_name", new MockHttpServletRequest(), null)
        bumperModelRepository.save(new VersionBumperModel(SemanticVersionBumper.getName(), 'semver', 'semver'))
    }

    @Transactional
    def 'full workflow'() {
        when:
        def repo = repoEndpoint.createRepo("project_name", "repo_name", new RestRepoCreateModel("semver", 'url'), null)

        then:
        repo.statusCode == HttpStatus.CREATED

        and:
        repoEndpoint.createRepo("project_name", "repo_name", new RestRepoCreateModel("semver", 'url'), null)

        then:
        repo.statusCode == HttpStatus.CONFLICT
    }

}
