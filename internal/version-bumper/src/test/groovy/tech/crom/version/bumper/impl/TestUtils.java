package tech.crom.version.bumper.impl;

import tech.crom.model.commit.VersionDetails;

public class TestUtils {

    private TestUtils() {

    }

    public static VersionDetails createVersionDetails() {
        return createVersionDetails("1.2.3");
    }

    public static VersionDetails createVersionDetails(String version) {
        return new VersionDetails(version);
    }
}
