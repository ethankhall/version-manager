package io.ehdev.conrad.version.bumper.api

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.version.bumper.SemanticVersionBumper
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
}
