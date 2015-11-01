package io.ehdev.conrad.backend.commit.matcher;

import io.ehdev.conrad.backend.version.commit.CommitVersionBumper;
import io.ehdev.conrad.backend.version.commit.VersionFactory;
import io.ehdev.conrad.backend.version.commit.internal.ForcedVersionBumper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForceVersionSquareBracketCommitMatcher implements GlobalCommitMatcherProvider {

    private final Pattern pattern = Pattern.compile(".*?\\[\\s*force version\\s*(.*?)\\s*\\].*?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private String version;

    @Override
    public CommitVersionBumper getBumper() {
        return new ForcedVersionBumper(VersionFactory.parse(version));
    }

    @Override
    public boolean matches(String string) {
        Matcher matcher = pattern.matcher(string);
        if(matcher.matches()) {
            version = matcher.group(1);
            return true;
        }
        return false;
    }
}
