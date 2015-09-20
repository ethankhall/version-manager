package io.ehdev.version.commit;

public interface CommitVersion extends Comparable<CommitVersion> {
    int getMajor();

    int getMinor();

    int getPatch();

    int getBuild();

    String getPostFix();

    int getGroup(VersionGroup versionGroup);

    CommitVersion bump(VersionGroup versionGroup);

    CommitVersion bumpBuild();

    CommitVersion bumpPatch();

    CommitVersion bumpMinor();

    CommitVersion bumpMajor();

    CommitVersion withPostfix(String postfix);

    CommitVersion asSnapshot();

    String toVersionString();
}
