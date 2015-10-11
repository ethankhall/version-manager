package io.ehdev.conrad.commit.matcher;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
