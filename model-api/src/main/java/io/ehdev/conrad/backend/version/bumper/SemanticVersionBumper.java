package io.ehdev.conrad.backend.version.bumper;


import io.ehdev.conrad.backend.commit.CommitDetails;
import io.ehdev.conrad.backend.commit.matcher.SquareBracketCommitMatcher;
import io.ehdev.conrad.backend.commit.matcher.WildcardSquareBracketCommitMatcher;
import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.StandardVersionGroupBump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.ehdev.conrad.backend.commit.matcher.StandardMessageMatchers.allMatchers;

@Service
public class SemanticVersionBumper implements VersionBumper {

    private static final Logger log = LoggerFactory.getLogger(SemanticVersionBumper.class);

    @Override
    public CommitVersion createNextVersion(CommitVersion parentVersion, CommitDetails commitDetails) {
        CommitVersion nextVersion = findNextVersion(parentVersion, commitDetails);
        log.info("Next version for commit {} is {}", commitDetails.getCommitId(), nextVersion.toVersionString());
        return nextVersion;
    }

    private CommitVersion findNextVersion(CommitVersion parentVersion, CommitDetails commitDetails) {
        Optional<SquareBracketCommitMatcher> matcher = allMatchers().stream().filter(commitDetails::messageContains).findFirst();
        WildcardSquareBracketCommitMatcher wildcardMatcher = new WildcardSquareBracketCommitMatcher();

        if(matcher.isPresent()) {
            return parentVersion.bump(matcher.get().getBumper());
        } else if (commitDetails.messageContains(wildcardMatcher)) {
            return parentVersion.bump(wildcardMatcher.getBumper());
        } else {
            return createDefaultNextVersion(parentVersion);
        }
    }

    @Override
    public CommitVersion createDefaultNextVersion(CommitVersion parentVersion) {
        return parentVersion.bump(StandardVersionGroupBump.patchVersion());
    }
}
