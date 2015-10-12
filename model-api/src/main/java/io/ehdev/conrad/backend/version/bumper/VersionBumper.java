package io.ehdev.conrad.backend.version.bumper;

import io.ehdev.conrad.backend.commit.CommitDetails;
import io.ehdev.conrad.backend.version.commit.CommitVersion;

public interface VersionBumper {
    CommitVersion createNextVersion(CommitVersion parentVersion, CommitDetails commitDetails);

    CommitVersion createDefaultNextVersion(CommitVersion parentVersion);
}
