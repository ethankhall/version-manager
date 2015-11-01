package io.ehdev.conrad.backend.commit.matcher;

import io.ehdev.conrad.backend.version.commit.CommitVersionBumper;
import io.ehdev.conrad.backend.version.commit.internal.DefaultVersionCommitBumper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WildcardSquareBracketCommitMatcher implements GlobalCommitMatcherProvider {

    private final Pattern pattern = Pattern.compile(".*?\\[\\s*bump group (\\d+)\\s*\\].*?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private Integer groupNumber = null;

    @Override
    public CommitVersionBumper getBumper() {
        if(null == groupNumber) {
            throw new GroupNotFoundException();
        }
        return new DefaultVersionCommitBumper(groupNumber);
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
        public GroupNotFoundException() {
            super("Unable to find group from message");
        }
    }
}
