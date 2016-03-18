package io.ehdev.conrad.version.bumper.semver;

import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.CommitVersionFactory;
import io.ehdev.conrad.version.commit.CommitVersionGroup;

public class SemanticCommitVersionFactory implements CommitVersionFactory<SemanticCommitVersion> {
    @Override
    public CommitVersionBumper<SemanticCommitVersion> dispenseBumper(int group) {
        return new SemanticCommitBumperContainer.SemverVersionCommitBumper(group);
    }

    @Override
    public CommitVersionBumper<SemanticCommitVersion> dispenseBumper(CommitVersionGroup group) {
        return new SemanticCommitBumperContainer.SemverVersionCommitBumper(group);
    }

    @Override
    public SemanticCommitVersion parseVersion(String version) {
        return SemanticCommitVersion.parse(version);
    }

}
