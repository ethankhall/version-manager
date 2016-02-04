package io.ehdev.conrad.version.bumper.api
import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel
import io.ehdev.conrad.database.model.project.ApiRepoModel
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel
import io.ehdev.conrad.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.version.commit.VersionFactory
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
        def apiModel = new ApiRepoModel('project', 'repo')

        when:
        def version = service.findNextVersion(apiModel, 'commitId', 'some message', VersionFactory.parse('1.2.3'))

        then:
        repoManagementApi.getDetails(_) >> Optional.of(new ApiRepoDetailsModel(new ApiRepoModel('project', 'repo'), bumperModel))
        version.toVersionString() == '1.2.4'
    }
}
