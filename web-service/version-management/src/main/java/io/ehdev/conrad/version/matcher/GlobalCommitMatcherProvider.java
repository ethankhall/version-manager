package io.ehdev.conrad.version.matcher;

import io.ehdev.conrad.version.commit.CommitVersion;

public interface GlobalCommitMatcherProvider<T extends CommitVersion> extends CommitMessageMatcher, CommitBumperProvider<T> {
}
