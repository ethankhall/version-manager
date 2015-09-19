package io.ehdev.version.update;

import io.ehdev.version.model.CommitVersion;


public interface VersionBumper {
    CommitVersion bump(CommitVersion parent, CommitDetails commitDetails);
}
