package io.ehdev.conrad.commit.matcher;

import io.ehdev.conrad.version.commit.CommitVersionBumper;

public interface CommitBumperProvider {
    CommitVersionBumper getBumper();
}
