package io.ehdev.conrad.version.matcher;

import io.ehdev.conrad.version.commit.CommitVersionBumper;

public interface CommitBumperProvider {
    CommitVersionBumper getBumper();
}
