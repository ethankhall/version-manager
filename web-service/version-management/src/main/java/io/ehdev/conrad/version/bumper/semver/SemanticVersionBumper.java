package io.ehdev.conrad.version.bumper.semver;


import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.details.CommitDetails;
import io.ehdev.conrad.version.matcher.GlobalCommitMatcherProvider;
import io.ehdev.conrad.version.matcher.internal.ForceVersionSquareBracketCommitMatcher;
import io.ehdev.conrad.version.matcher.internal.SquareBracketCommitMatcher;
import io.ehdev.conrad.version.matcher.internal.WildcardSquareBracketCommitMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SemanticVersionBumper implements VersionBumper {

    private static final Logger log = LoggerFactory.getLogger(SemanticVersionBumper.class);

    @Override
    public CommitVersion createNextVersion(String parentVersion, CommitDetails commitDetails) {
        CommitVersion nextVersion = findNextVersion(SemanticCommitVersion.parse(parentVersion), commitDetails);
        log.info("Next version for commit {} is {}", commitDetails.getCommitId(), nextVersion.toVersionString());
        return nextVersion;
    }

    private CommitVersion findNextVersion(SemanticCommitVersion parentVersion, CommitDetails commitDetails) {
        Optional<SquareBracketCommitMatcher<SemanticCommitVersion>> matcher =
            SemanticCommitBumperContainer
                .getAllSquareBracketMatchers()
                .stream()
                .filter(commitDetails::messageContains)
                .findFirst();

        if (matcher.isPresent()) {
            return matcher.get().getBumper().bump(parentVersion);
        }

        SemanticCommitVersionFactory factory = new SemanticCommitVersionFactory();

        List<GlobalCommitMatcherProvider<SemanticCommitVersion>> globalMatchers = Arrays.asList(
            new WildcardSquareBracketCommitMatcher<>(factory),
            new ForceVersionSquareBracketCommitMatcher<>(factory)
        );

        Optional<GlobalCommitMatcherProvider<SemanticCommitVersion>> globalMatcher =
            globalMatchers
                .stream()
                .filter(commitDetails::messageContains)
                .findFirst();

        if (globalMatcher.isPresent()) {
            return globalMatcher.get().getBumper().bump(parentVersion);
        } else {
            return createDefaultNextVersion(parentVersion);
        }
    }

    @Override
    public CommitVersion createDefaultNextVersion(String parentVersion) {
        return createDefaultNextVersion(SemanticCommitVersion.parse(parentVersion));
    }

    public CommitVersion createDefaultNextVersion(SemanticCommitVersion parentVersion) {
        return new SemanticCommitBumperContainer.BumpLowestBumper().bump(parentVersion);
    }
}
