package io.ehdev.conrad.service.api.project;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.bumper.api.DefaultVersionBumperService;

import java.util.Set;

public class TestDoubleVersionBumperService extends DefaultVersionBumperService {
    public TestDoubleVersionBumperService(Set<VersionBumper> bumperSet, RepoManagementApi repoManagementApi) {
        super(bumperSet, repoManagementApi);
    }
}
