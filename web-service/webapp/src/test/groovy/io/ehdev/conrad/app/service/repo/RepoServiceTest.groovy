package io.ehdev.conrad.app.service.repo

import io.ehdev.conrad.app.manager.RepoManager
import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.model.repo.RepoHistory
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static io.ehdev.conrad.test.MockMvcDefaults.makeGetRequest
import static io.ehdev.conrad.test.MockMvcDefaults.makePostRequest
import static io.ehdev.conrad.test.database.repository.PopulateTestUtils.stubBumper
import static io.ehdev.conrad.test.database.repository.PopulateTestUtils.stubRepo

class RepoServiceTest extends Specification {

    MockMvc mockMvc
    RepoService repoService
    RepoManager repoManager

    def setup() {
        repoManager = Mock(RepoManager)
        repoService = new RepoService(repoManager)
        mockMvc = MockMvcBuilders.standaloneSetup(repoService).build()
    }

    def 'a GET on the repo will return all repos'() {
        given:
        def bumper = stubBumper()
        def repos = [stubRepo(bumper), stubRepo(bumper), stubRepo(bumper)]
        repoManager.getAll() >> repos

        when:
        def json = makeGetRequest(mockMvc, '/api/repo')

        then:
        repos.each { repo ->
            assert json[repo.getId().toString()] != null
            json[repo.getId().toString()]['bumper'] == bumper.getBumperName()
            json[repo.getId().toString()]['id'] == repo.getId().toString()
        }
    }

    def 'can POST to the end point'() {
        given:
        def map = [name: 'repo name', url: 'some github repo', bumper: SemanticVersionBumper.getSimpleName()]
        def repo = stubRepo(stubBumper(), map.name)
        repo.setUrl(map.url)

        when:
        def json = makePostRequest(mockMvc, '/api/repo', map)

        then:
        map.each { key, value ->
            assert json[key] == value
        }
        1 * repoManager.createRepo(map.name, map.bumper, map.url) >> repo
        0 * repoManager.setupHistory(repo, _)
    }

    def 'can POST to the end point with history'() {
        given:
        def map = [name: 'repo name', url: 'some github repo', bumper: SemanticVersionBumper.getSimpleName(), history: [new RepoHistory("1.2.3", "abc"), new RepoHistory("1.2.4", "456")]]
        def repo = stubRepo(stubBumper(), map.name)
        repo.setUrl(map.url)

        when:
        def json = makePostRequest(mockMvc, '/api/repo', map)

        then:
        map.each { key, value ->
            if(key == 'history') { return }
            assert json[key] == value
        }
        1 * repoManager.createRepo(map.name, map.bumper, map.url) >> repo
        1 * repoManager.setupHistory(repo, _)
    }
}
