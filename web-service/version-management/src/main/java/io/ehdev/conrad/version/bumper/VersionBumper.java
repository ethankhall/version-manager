package io.ehdev.conrad.version.bumper;

import io.ehdev.conrad.version.commit.details.CommitDetails;
import io.ehdev.conrad.version.commit.CommitVersion;

public interface VersionBumper {
    CommitVersion createNextVersion(CommitVersion parentVersion, CommitDetails commitDetails);

    CommitVersion createDefaultNextVersion(CommitVersion parentVersion);
}
