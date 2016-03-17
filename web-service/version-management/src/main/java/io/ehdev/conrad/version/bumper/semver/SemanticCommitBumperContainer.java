package io.ehdev.conrad.version.bumper.semver;

import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.CommitVersionGroup;
import io.ehdev.conrad.version.matcher.internal.SquareBracketCommitMatcher;
import io.ehdev.conrad.version.matcher.internal.StandardMessageMatchers;

import java.util.Arrays;
import java.util.List;

public class SemanticCommitBumperContainer {

    private SemanticCommitBumperContainer() { }

    static public class BumpLowestBumper implements CommitVersionBumper<SemanticCommitVersion> {
        @Override
        public SemanticCommitVersion bump(SemanticCommitVersion commitVersion) {
            int[] ints = Arrays.copyOf(commitVersion.getVersionList(), commitVersion.getVersionList().length);
            ints[commitVersion.getVersionList().length - 1]++;

            return new SemanticCommitVersion(ints, "SNAPSHOT");
        }
    }

    static public class SemverVersionCommitBumper implements CommitVersionBumper<SemanticCommitVersion> {

        private final int groupNumber;

        SemverVersionCommitBumper(CommitVersionGroup groupNumber) {
            this(groupNumber.getGroup());
        }

        SemverVersionCommitBumper(int groupNumber) {
            this.groupNumber = groupNumber;
        }

        @Override
        public SemanticCommitVersion bump(SemanticCommitVersion parent) {
            int[] nextVersionList = Arrays.copyOf(parent.getVersionList(), Math.max(3, groupNumber + 1));
            //Set the lower digits to 0
            for (int i = groupNumber + 1; i < parent.getVersionList().length; i++) {
                nextVersionList[i] = 0;
            }

            //Bump the version
            nextVersionList[groupNumber]++;

            return new SemanticCommitVersion(nextVersionList, null);
        }
    }

//    static public class PostfixCommitVersionBumper implements CommitVersionBumper<SemanticCommitVersion> {
//
//        private final String postfix;
//
//        public PostfixCommitVersionBumper(String postfix) {
//            this.postfix = postfix;
//        }
//
//        @Override
//        public SemanticCommitVersion bump(SemanticCommitVersion parent) {
//            return new SemanticCommitVersion(parent.getVersionList(), this.postfix);
//        }
//    }

    public static List<SquareBracketCommitMatcher<SemanticCommitVersion>> getAllSquareBracketMatchers() {
        StandardMessageMatchers<SemanticCommitVersion> matchers = new StandardMessageMatchers<>(new SemanticCommitVersionFactory());
        return Arrays.asList(
            matchers.majorVersion(),
            matchers.minorVersion(),
            matchers.patchVersion(),
            matchers.buildVersion()
        );
    }
}
