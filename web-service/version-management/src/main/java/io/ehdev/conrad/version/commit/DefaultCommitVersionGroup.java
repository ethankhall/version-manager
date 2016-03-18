package io.ehdev.conrad.version.commit;

public class DefaultCommitVersionGroup implements CommitVersionGroup {

    private final int group;

    private DefaultCommitVersionGroup(int group) {
        this.group = group;
    }

    @Override
    public int getGroup() {
        return group;
    }

    public static DefaultCommitVersionGroup majorVersion() {
        return new DefaultCommitVersionGroup(0);
    }

    public static DefaultCommitVersionGroup minorVersion() {
        return new DefaultCommitVersionGroup(1);
    }

    public static DefaultCommitVersionGroup patchVersion() {
        return new DefaultCommitVersionGroup(2);
    }

    public static DefaultCommitVersionGroup buildVersion() {
        return new DefaultCommitVersionGroup(3);
    }
}
