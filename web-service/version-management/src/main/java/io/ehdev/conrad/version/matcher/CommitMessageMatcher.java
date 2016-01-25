package io.ehdev.conrad.version.matcher;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
