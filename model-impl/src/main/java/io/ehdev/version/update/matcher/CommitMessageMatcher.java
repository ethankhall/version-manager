package io.ehdev.version.update.matcher;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
