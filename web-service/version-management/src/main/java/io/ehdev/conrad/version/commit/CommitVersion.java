package io.ehdev.conrad.version.commit;

import java.util.List;

public interface CommitVersion extends Comparable<CommitVersion> {

    CommitVersion bump(CommitVersionBumper defaultCommitVersionBumper);

    String toVersionString();

    List<Integer> getVersionGroup();

    Integer getGroup(CommitVersionGroup versionGroup);

    String getPostFix();
}
