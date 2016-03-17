package io.ehdev.conrad.version.matcher.internal;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.CommitVersionFactory;
import io.ehdev.conrad.version.matcher.GlobalCommitMatcherProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WildcardSquareBracketCommitMatcher<T extends CommitVersion> implements GlobalCommitMatcherProvider<T> {

    private final Pattern pattern = Pattern.compile(".*?\\[\\s*bump group (\\d+)\\s*\\].*?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private final CommitVersionFactory<T> factory;

    private Integer groupNumber = null;

    public WildcardSquareBracketCommitMatcher(CommitVersionFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public CommitVersionBumper<T> getBumper() {
        if(null == groupNumber) {
            throw new GroupNotFoundException();
        }
        return factory.dispenseBumper(groupNumber);
    }

    @Override
    public boolean matches(String string) {
        Matcher matcher = pattern.matcher(string);
        if(matcher.matches()) {
            groupNumber = Integer.parseInt(matcher.group(1)) - 1;
            return true;
        }
        return false;
    }

    public static class GroupNotFoundException extends RuntimeException {
        GroupNotFoundException() {
            super("Unable to find group from message");
        }
    }
}
