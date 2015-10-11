package io.ehdev.version.model;

public interface CommitVersionBumper {
    CommitVersion bump(int[] groups, String postfix);
}
