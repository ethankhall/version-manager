package io.ehdev.version.model.update.matcher;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
