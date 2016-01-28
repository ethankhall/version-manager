package io.ehdev.conrad.service.api.service

import io.ehdev.conrad.database.api.RepoManagementApi
import io.ehdev.conrad.version.bumper.api.VersionBumperService
import spock.lang.Specification

class RepoEndpointTest extends Specification {

    RepoEndpoint repoEndpoint
    RepoManagementApi repoManagementApi
    VersionBumperService versionBumperService

    def setup() {
        repoEndpoint = new RepoEndpoint()
    }
}
