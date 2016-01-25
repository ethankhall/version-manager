package io.ehdev.conrad.version.commit;

public interface CommitVersionBumper {
    CommitVersion bump(int[] groups, String postfix);
}
