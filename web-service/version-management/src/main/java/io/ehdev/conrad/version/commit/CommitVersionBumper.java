package io.ehdev.conrad.version.commit;

public interface CommitVersionBumper<T extends CommitVersion> {
    T bump(T previousVersion);
}
