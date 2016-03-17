package io.ehdev.conrad.version.commit;

import java.util.List;

public interface CommitVersion<T> extends Comparable<CommitVersion<T>> {

    String toVersionString();

    List<T> getVersionGroup();

    String getPostFix();

    T getGroup(CommitVersionGroup versionGroup);
}
