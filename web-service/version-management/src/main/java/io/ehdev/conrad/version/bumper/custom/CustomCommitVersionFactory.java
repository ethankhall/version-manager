package io.ehdev.conrad.version.bumper.custom;

import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.CommitVersionFactory;
import io.ehdev.conrad.version.commit.CommitVersionGroup;


public class CustomCommitVersionFactory implements CommitVersionFactory<CustomCommitVersion> {
    @Override
    public CommitVersionBumper<CustomCommitVersion> dispenseBumper(int group) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public CommitVersionBumper<CustomCommitVersion> dispenseBumper(CommitVersionGroup group) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public CustomCommitVersion parseVersion(String version) {
        return new CustomCommitVersion(version);
    }
}
