package io.ehdev.conrad.backend.commit.matcher;

import io.ehdev.conrad.backend.version.commit.StandardVersionGroupBump;

import java.util.Arrays;
import java.util.List;

public class StandardMessageMatchers {

    public static class SquareBracket {

        private static final SquareBracketCommitMatcher major = new SquareBracketCommitMatcher("major", StandardVersionGroupBump.majorVersion());
        private static final SquareBracketCommitMatcher minor = new SquareBracketCommitMatcher("minor", StandardVersionGroupBump.minorVersion());
        private static final SquareBracketCommitMatcher patch = new SquareBracketCommitMatcher("patch", StandardVersionGroupBump.patchVersion());
        private static final SquareBracketCommitMatcher build = new SquareBracketCommitMatcher("build", StandardVersionGroupBump.buildVersion());

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
