package io.ehdev.conrad.model.version;

import java.util.List;

public interface CommitVersion extends Comparable<CommitVersion> {

    CommitVersion bump(CommitVersionBumper defaultCommitVersionBumper);

    String toVersionString();

    List<Integer> getVersionGroup();

    Integer getGroup(VersionGroupCapture versionGroup);

    String getPostFix();
}
