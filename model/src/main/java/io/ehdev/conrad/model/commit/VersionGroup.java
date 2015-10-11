package io.ehdev.conrad.model.commit;

public enum VersionGroup {
    MAJOR(0),
    MINOR(1),
    PATCH(2),
    BUILD(3);

    int groupNumber;

    VersionGroup(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }
}
