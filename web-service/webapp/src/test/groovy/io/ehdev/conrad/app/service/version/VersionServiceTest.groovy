package io.ehdev.conrad.app.service.version
import groovy.json.JsonSlurper
import io.ehdev.conrad.app.manager.CommitManager
import io.ehdev.conrad.backend.version.commit.VersionFactory
import io.ehdev.conrad.database.model.CommitModel
import io.ehdev.conrad.test.MvcControllerCreator
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class VersionServiceTest extends Specification {
    MockMvc mockMvc
    VersionService versionService
    CommitManager commitManager

    def setup() {
        commitManager = Mock(CommitManager)
        versionService = new VersionService(commitManager)
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
        def results = mockMvc.perform(get("/api/version/${uuid.toString()}"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn()
        def json = new JsonSlurper().parseText(results.getResponse().getContentAsString())

        println json

        then:
        json != null
        json.commits.size() == 10
        commits.reverse().eachWithIndex { it, idx ->
            assert json.commits[idx] != null
            assert json.commits[idx]['commit'] == it.commitId
            assert json.commits[idx]['version'] == it.getVersion().toString()
        }
    }

    CommitModel createCommit(String version, CommitModel parent = null){
        return new CommitModel(RandomStringUtils.randomAlphanumeric(40), null, VersionFactory.parse(version), parent)
    }
}
