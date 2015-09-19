package io.ehdev.version.update.message;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
