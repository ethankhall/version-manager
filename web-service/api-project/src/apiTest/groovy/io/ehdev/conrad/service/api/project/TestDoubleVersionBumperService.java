package io.ehdev.conrad.service.api.project;

import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.version.bumper.semver.SemanticVersionBumper;
import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.bumper.api.VersionBumperService;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.details.DefaultCommitDetails;

public class TestDoubleVersionBumperService implements VersionBumperService {

    @Override
    public VersionBumper findVersionBumper(String className) {
        return new SemanticVersionBumper();
    }

    @Override
    public CommitVersion findNextVersion(ApiRepoModel repoModel, String commitId, String message, CommitVersion lastCommit) {
        VersionBumper versionBumper = findVersionBumper(null);
        DefaultCommitDetails commitDetails = new DefaultCommitDetails(commitId, message);
        return versionBumper.createNextVersion(lastCommit, commitDetails);
    }
}
