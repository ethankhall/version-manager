package io.ehdev.version.update;

import io.ehdev.version.model.CommitVersion;
import io.ehdev.version.model.VersionGroup;
import io.ehdev.version.update.message.SquareBracketMatcher;


public class SemverVersionBump implements VersionBumper {

    @Override
    public CommitVersion bump(CommitVersion parent, CommitDetails commitDetails) {
        VersionGroup requestedBumpType = findVersionBumpForCommit(commitDetails);
        return null;
    }

    private VersionGroup findVersionBumpForCommit(CommitDetails commitDetails) {
        for (VersionGroup versionGroup : VersionGroup.values()) {
            if(commitDetails.messageContains(new SquareBracketMatcher(versionGroup))) {
                return versionGroup;
            }
        }
        return VersionGroup.PATCH;
    }
}
