package io.ehdev.conrad.version.matcher;

import io.ehdev.conrad.version.commit.CommitVersionBumper;
import org.apache.commons.lang3.StringUtils;


public class SquareBracketCommitMatcher implements GlobalCommitMatcherProvider {

    private final String expectedString;
    private final CommitVersionBumper bumper;

    public SquareBracketCommitMatcher(String groupName, CommitVersionBumper bumper) {
        String expectedString = StringUtils.trimToEmpty(String.format("bump %s", groupName.toLowerCase()));
        expectedString = StringUtils.removeStart(expectedString, "[");
        expectedString = StringUtils.removeEnd(expectedString, "]");
        expectedString = StringUtils.trimToEmpty(expectedString);

        if(groupName.isEmpty()) {
            throw new InvalidSearchStringException();
        }

        this.expectedString = String.format("[%s]", expectedString);
        this.bumper = bumper;
    }

    public CommitVersionBumper getBumper() {
        return bumper;
    }

    @Override
    public boolean matches(String haystack) {
        return StringUtils.containsIgnoreCase(haystack, expectedString);
    }

    public static class InvalidSearchStringException extends RuntimeException {
        public InvalidSearchStringException() {
            super("Unable to search for empty string");
        }
    }
}
