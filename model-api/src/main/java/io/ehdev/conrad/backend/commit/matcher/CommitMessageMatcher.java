package io.ehdev.conrad.backend.commit.matcher;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
