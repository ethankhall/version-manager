package io.ehdev.conrad.version.matcher;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;

public interface CommitBumperProvider<T extends CommitVersion> {
    CommitVersionBumper<T> getBumper();
}
