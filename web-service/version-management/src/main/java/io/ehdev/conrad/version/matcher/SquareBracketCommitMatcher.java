package io.ehdev.conrad.version.matcher;

import io.ehdev.conrad.version.commit.CommitVersionBumper;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;


public class SquareBracketCommitMatcher implements GlobalCommitMatcherProvider {

    private final CommitVersionBumper bumper;
    private final Pattern pattern;

    public SquareBracketCommitMatcher(String groupName, CommitVersionBumper bumper) {
        String expectedString = StringUtils.trimToEmpty(String.format("bump %s", groupName.toLowerCase()));
        expectedString = StringUtils.removeStart(expectedString, "[");
        expectedString = StringUtils.removeEnd(expectedString, "]");
        expectedString = StringUtils.trimToEmpty(expectedString);

        if(groupName.isEmpty()) {
            throw new InvalidSearchStringException();
        }

        this.pattern = Pattern.compile(String.format(".*?\\[\\s*%s\\s*\\].*?", expectedString), Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        this.bumper = bumper;
    }

    public CommitVersionBumper getBumper() {
        return bumper;
    }

    @Override
    public boolean matches(String haystack) {
        return pattern.matcher(haystack).matches();
    }

    public static class InvalidSearchStringException extends RuntimeException {
        public InvalidSearchStringException() {
            super("Unable to search for empty string");
        }
    }
}
