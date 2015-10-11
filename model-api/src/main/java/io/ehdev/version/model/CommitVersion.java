package io.ehdev.version.model;

import java.util.List;

public interface CommitVersion extends Comparable<CommitVersion> {

    CommitVersion bump(CommitVersionBumper defaultCommitVersionBumper);

    String toVersionString();

    List<Integer> getVersionGroup();

    Integer getGroup(VersionGroup versionGroup);

    String getPostFix();
}
