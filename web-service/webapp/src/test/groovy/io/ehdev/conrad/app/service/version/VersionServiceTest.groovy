package io.ehdev.conrad.app.service.version

import io.ehdev.conrad.app.manager.CommitManager
import io.ehdev.conrad.app.test.MvcControllerCreator
import io.ehdev.conrad.backend.version.commit.VersionFactory
import io.ehdev.conrad.model.version.VersionSearchModel
import io.ehdev.conrad.test.MockMvcDefaults
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static io.ehdev.conrad.test.database.repository.MockTestUtils.createCommit

class VersionServiceTest extends Specification {
    MockMvc mockMvc
    DefaultVersionService versionService
    CommitManager commitManager

    def setup() {
        commitManager = Mock(CommitManager)
        versionService = new DefaultVersionService(commitManager)
        mockMvc = MvcControllerCreator.createMockMvc(versionService)
    }

    def 'list all commits for a repo'() {
        def uuid = UUID.randomUUID()
        def parentCommit = null
        def commits = (0..9).collect {
            def temp = createCommit('1.2.' + it, parentCommit)
            parentCommit = temp;
            return temp
        }
        commitManager.findCommitsForRepo(_) >> commits

        when:
        def json = MockMvcDefaults.makeGetRequest(mockMvc, "/api/version/${uuid.toString()}")

        then:
        json != null
        json.commits.size() == 10
        commits.reverse().eachWithIndex { it, idx ->
            assert json.commits[idx] != null
            assert json.commits[idx]['commit'] == it.commitId
            assert json.commits[idx]['version'] == it.getVersion().toString()
        }
    }

    def 'search for next commit'() {
        def uuid = UUID.randomUUID()
        commitManager.findVersion(uuid, _ as VersionSearchModel) >> VersionFactory.parse('1.3.5')

        when:
        def json = MockMvcDefaults.makePostRequest(mockMvc, "/api/version/${uuid.toString()}/search", new VersionSearchModel([]))

        then:
        json != null
        json.version == '1.3.6-SNAPSHOT'
    }
}
