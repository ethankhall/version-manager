package io.ehdev.conrad.version.commit;

public interface CommitVersionFactory<T extends CommitVersion> {

    CommitVersionBumper<T> dispenseBumper(int group);

    CommitVersionBumper<T> dispenseBumper(CommitVersionGroup group);

    T parseVersion(String version);
}
