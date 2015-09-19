package io.ehdev.version.update;

import io.ehdev.version.update.message.CommitMessageMatcher;


public interface CommitDetails {
    boolean messageContains(CommitMessageMatcher search);
}
