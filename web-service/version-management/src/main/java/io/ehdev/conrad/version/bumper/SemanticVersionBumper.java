package io.ehdev.conrad.version.bumper;


import io.ehdev.conrad.version.commit.details.CommitDetails;
import io.ehdev.conrad.version.matcher.*;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.StandardVersionGroupBump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.ehdev.conrad.version.matcher.StandardMessageMatchers.allMatchers;

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

        if(matcher.isPresent()) {
            return parentVersion.bump(matcher.get().getBumper());
        }

        List<GlobalCommitMatcherProvider> globalMatchers = Arrays.asList(new WildcardSquareBracketCommitMatcher(), new ForceVersionSquareBracketCommitMatcher());
        Optional<GlobalCommitMatcherProvider> globalMatcher = globalMatchers.stream().filter(commitDetails::messageContains).findFirst();

        if(globalMatcher.isPresent()) {
            return parentVersion.bump(globalMatcher.get().getBumper());
        } else {
            return createDefaultNextVersion(parentVersion);
        }
    }

    @Override
    public CommitVersion createDefaultNextVersion(CommitVersion parentVersion) {
        return parentVersion.bump(StandardVersionGroupBump.patchVersion());
    }
}
