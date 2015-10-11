package io.ehdev.conrad.model.update.matcher;

public interface CommitMessageMatcher {

    boolean matches(String string);
}
