package io.ehdev.conrad.version.matcher;

import io.ehdev.conrad.version.commit.StandardVersionGroupBump;

import java.util.Arrays;
import java.util.List;

public class StandardMessageMatchers {

    public static class SquareBracket {

        private static final SquareBracketCommitMatcher major = new SquareBracketCommitMatcher("major( version)?", StandardVersionGroupBump.majorVersion());
        private static final SquareBracketCommitMatcher minor = new SquareBracketCommitMatcher("minor( version)?", StandardVersionGroupBump.minorVersion());
        private static final SquareBracketCommitMatcher patch = new SquareBracketCommitMatcher("patch( version)?", StandardVersionGroupBump.patchVersion());
        private static final SquareBracketCommitMatcher build = new SquareBracketCommitMatcher("build( version)?", StandardVersionGroupBump.buildVersion());

        public static SquareBracketCommitMatcher minor() {
            return minor;
        }

        public static SquareBracketCommitMatcher major() {
            return major;
        }

        public static SquareBracketCommitMatcher patch() {
            return patch;
        }

        public static SquareBracketCommitMatcher build() {
            return build;
        }
    }

    public static List<SquareBracketCommitMatcher> allMatchers() {
        return Arrays.asList(SquareBracket.major(), SquareBracket.minor(), SquareBracket.patch(), SquareBracket.build());
    }
}
