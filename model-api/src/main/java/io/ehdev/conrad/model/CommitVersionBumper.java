package io.ehdev.conrad.model;

public interface CommitVersionBumper {
    CommitVersion bump(int[] groups, String postfix);
}
