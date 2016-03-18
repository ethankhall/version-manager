package io.ehdev.conrad.version.bumper.api


import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel
import io.ehdev.conrad.database.model.project.DefaultApiRepoModel
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import io.ehdev.conrad.version.bumper.semver.SemanticVersionBumper
import spock.lang.Specification

class DefaultVersionBumperServiceTest extends Specification {

    RepoManagementApi repoManagementApi
    DefaultVersionBumperService service

    def setup() {
        repoManagementApi = Mock(RepoManagementApi)
        service = new DefaultVersionBumperService([new SemanticVersionBumper()] as Set, repoManagementApi)
    }

    def 'can find by name'() {
        expect:
        service.findVersionBumper(SemanticVersionBumper.getName()).getClass() == SemanticVersionBumper
    }

    def 'can find next version'() {
        def bumperModel = new ApiVersionBumperModel(SemanticVersionBumper.name, ' ', 'semver')
        def apiModel = new DefaultApiRepoModel('project', 'repo')

        when:
        def version = service.findNextVersion(apiModel, 'commitId', 'some message', new ApiCommitModel('foo', '1.2.3'))

        then:
        repoManagementApi.getDetails(_) >> Optional.of(new ApiRepoDetailsModel(new DefaultApiRepoModel('project', 'repo'), bumperModel))
        version.toVersionString() == '1.2.4'
    }
}
