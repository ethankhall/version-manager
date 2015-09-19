package io.ehdev.version.update.message;

import io.ehdev.version.model.VersionGroup;
import org.apache.commons.lang3.StringUtils;


public class SquareBracketMatcher implements CommitMessageMatcher {

    private final String expectedString;

    public SquareBracketMatcher(String expectedString) {
        expectedString = StringUtils.trimToEmpty(expectedString);
        expectedString = StringUtils.removeStart(expectedString, "[");
        expectedString = StringUtils.removeEnd(expectedString, "]");
        expectedString = StringUtils.trimToEmpty(expectedString);

        if(expectedString.isEmpty()) {
            throw new InvalidSearchStringException();
        }

        this.expectedString = String.format("[%s]", expectedString);
    }

    public SquareBracketMatcher(VersionGroup versionGroup) {
        this(String.format("bump %s", versionGroup.name().toLowerCase()));
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
