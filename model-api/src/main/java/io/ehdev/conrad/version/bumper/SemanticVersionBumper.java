package io.ehdev.conrad.version.bumper;


import io.ehdev.conrad.commit.CommitDetails;
import io.ehdev.conrad.commit.matcher.SquareBracketCommitMatcher;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.internal.StandardVersionGroupBump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.ehdev.conrad.commit.matcher.StandardMessageMatchers.allMatchers;

@Service
public class SemanticVersionBumper implements VersionBumper {

    private static final Logger log = LoggerFactory.getLogger(SemanticVersionBumper.class);

    @Override
    public CommitVersion createNextVersion(CommitVersion parentVersion, CommitDetails commitDetails) {
        Optional<SquareBracketCommitMatcher> matcher = allMatchers().stream().filter(commitDetails::messageContains).findFirst();
        CommitVersion nextVersion = findNextVersion(parentVersion, matcher);
        log.info("Next version for commit {} is {}", commitDetails.getCommitId(), nextVersion.toVersionString());
        return nextVersion;
    }

    private CommitVersion findNextVersion(CommitVersion parentVersion, Optional<SquareBracketCommitMatcher> matcher) {
        if(matcher.isPresent()) {
            return parentVersion.bump(matcher.get().getBumper());
        } else {
            return createDefaultNextVersion(parentVersion);
        }
    }

    @Override
    public CommitVersion createDefaultNextVersion(CommitVersion parentVersion) {
        return parentVersion.bump(StandardVersionGroupBump.patchVersion());
    }
}
