package io.ehdev.conrad.version.matcher.internal;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionFactory;
import io.ehdev.conrad.version.commit.DefaultCommitVersionGroup;

public class StandardMessageMatchers<T extends CommitVersion> {

    private final CommitVersionFactory<T> factory;

    public StandardMessageMatchers(CommitVersionFactory<T> factory) {
        this.factory = factory;
    }

    public SquareBracketCommitMatcher<T> majorVersion() {
        return new SquareBracketCommitMatcher<>("major( version)?", factory.dispenseBumper(DefaultCommitVersionGroup.majorVersion()));
    }

    public SquareBracketCommitMatcher<T> minorVersion() {
        return new SquareBracketCommitMatcher<>("minor( version)?", factory.dispenseBumper(DefaultCommitVersionGroup.minorVersion()));
    }

    public SquareBracketCommitMatcher<T> patchVersion() {
        return new SquareBracketCommitMatcher<>("patch( version)?", factory.dispenseBumper(DefaultCommitVersionGroup.patchVersion()));
    }

    public SquareBracketCommitMatcher<T> buildVersion() {
        return new SquareBracketCommitMatcher<>("build( version)?", factory.dispenseBumper(DefaultCommitVersionGroup.buildVersion()));
    }
}
