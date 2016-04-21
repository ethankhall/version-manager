package io.ehdev.conrad.version.bumper.custom;

import io.ehdev.conrad.version.bumper.VersionBumper;
import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.details.CommitDetails;
import io.ehdev.conrad.version.matcher.CommitBumperProvider;
import io.ehdev.conrad.version.matcher.GlobalCommitMatcherProvider;
import io.ehdev.conrad.version.matcher.internal.ForceVersionSquareBracketCommitMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CustomNumberBumper implements VersionBumper {

    @Override
    public CommitVersion createNextVersion(String parentVersion, CommitDetails commitDetails) {
        GlobalCommitMatcherProvider<CustomCommitVersion> matcher = new ForceVersionSquareBracketCommitMatcher<>(new CustomCommitVersionFactory());
        if(commitDetails.messageContains(matcher)) {
            return matcher.getBumper().bump(null);
        }

        throw new VersionNotSpecifiedException();
    }

    @Override
    public CommitVersion createDefaultNextVersion(String parentVersion) {
        throw new VersionNotSpecifiedException();
    }

    public static class VersionNotSpecifiedException extends RuntimeException {
        public VersionNotSpecifiedException() {
            super("You are using a custom versioning system, you must specify the next version");
        }
    }
}
