package io.ehdev.conrad.backend.commit.matcher;

import io.ehdev.conrad.backend.version.commit.CommitVersionBumper;

public interface CommitBumperProvider {
    CommitVersionBumper getBumper();
}
