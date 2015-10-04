package io.ehdev.version.update.semver;

import io.ehdev.version.commit.CommitDetails;
import io.ehdev.version.commit.CommitVersion;
import io.ehdev.version.commit.RepositoryCommit;
import io.ehdev.version.commit.VersionGroup;
import io.ehdev.version.update.NextVersion;
import io.ehdev.version.update.VersionBumper;
import io.ehdev.version.update.DefaultNextVersion;
import io.ehdev.version.update.matcher.SquareBracketMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SemverVersionBumper implements VersionBumper {

    private static final Logger log = LoggerFactory.getLogger(SemverVersionBumper.class);

    @Override
    public NextVersion createNextVersion(RepositoryCommit parent, CommitDetails commitDetails) {
        if(null != parent.getNextCommit() && null != parent.getBugfixCommit()) {
            throw new UnableToBumpVersionDueToFullCommitException(parent.getCommitId());
        }

        VersionGroup versionGroup = findVersionGroupForBump(commitDetails);
        CommitVersion nextVersion;
        NextVersion.Type commitType;
        if(null != parent.getNextCommit() && versionGroup != VersionGroup.BUILD) {
            log.info("Commit {}, has next commit. Forcing to bugfix.", parent.getCommitId());
            versionGroup = VersionGroup.BUILD;
            commitType = NextVersion.Type.BUGFIX;
        } else if (null == versionGroup && parent.getVersion().getBuild() != 0) {
            commitType = NextVersion.Type.BUGFIX;
            versionGroup = VersionGroup.BUILD;
        } else {
            versionGroup = VersionGroup.PATCH;
            commitType = NextVersion.Type.NEXT;
        }
        nextVersion = parent.getVersion().bump(versionGroup);

        log.info("Commit {} will be assigned version {}", commitDetails.getCommitId(), nextVersion.toString());

        return new DefaultNextVersion(nextVersion, commitType);
    }

    @Override
    public CommitVersion createBuildVersion(RepositoryCommit parentCommit) {
        if(0 != parentCommit.getVersion().getBuild()) {
            return parentCommit.getVersion().bump(VersionGroup.BUILD).asSnapshot();
        } else {
            return parentCommit.getVersion().bump(VersionGroup.PATCH).asSnapshot();
        }
    }

    @Override
    public String name() {
        return SemverVersionBumper.class.getSimpleName();
    }

    VersionGroup findVersionGroupForBump(CommitDetails commitDetails) {
        for (VersionGroup versionGroup : VersionGroup.values()) {
            if(commitDetails.messageContains(new SquareBracketMatcher(versionGroup))) {
                return versionGroup;
            }
        }
        return null;
    }

    public static class UnableToBumpVersionDueToFullCommitException extends RuntimeException {
        public UnableToBumpVersionDueToFullCommitException(String commitId) {
            super("Unable to create commit for commit " + commitId);
        }
    }
}
