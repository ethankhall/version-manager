package io.ehdev.conrad.version.bumper;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.StandardVersionGroupBump;
import io.ehdev.conrad.version.commit.details.CommitDetails;
import io.ehdev.conrad.version.commit.internal.DefaultCommitVersion;
import io.ehdev.conrad.version.matcher.ForceVersionSquareBracketCommitMatcher;
import io.ehdev.conrad.version.matcher.GlobalCommitMatcherProvider;
import io.ehdev.conrad.version.matcher.SquareBracketCommitMatcher;
import io.ehdev.conrad.version.matcher.WildcardSquareBracketCommitMatcher;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static io.ehdev.conrad.version.matcher.StandardMessageMatchers.allMatchers;


@Service
public class AtomicNumberBumper implements VersionBumper {

    private static final Logger log = LoggerFactory.getLogger(AtomicNumberBumper.class);

    @Override
    public CommitVersion createNextVersion(CommitVersion parentVersion, CommitDetails commitDetails) {
        CommitVersion nextVersion = createDefaultNextVersion(parentVersion);
        log.info("Next version for commit {} is {}", commitDetails.getCommitId(), nextVersion.toVersionString());
        return nextVersion;
    }

    @Override
    public CommitVersion createDefaultNextVersion(CommitVersion parentVersion) {
        return parentVersion.bump((groups, postfix) -> new DefaultCommitVersion(new int[] { groups[0]  + 1}, null));
    }
}
