package io.ehdev.conrad.model.version;

public interface CommitVersionBumper {
    CommitVersion bump(int[] groups, String postfix);
}
