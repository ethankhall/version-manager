package io.ehdev.conrad.backend.version.commit;

public interface CommitVersionBumper {
    CommitVersion bump(int[] groups, String postfix);
}
