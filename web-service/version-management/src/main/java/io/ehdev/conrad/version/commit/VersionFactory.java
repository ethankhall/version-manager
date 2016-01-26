package io.ehdev.conrad.version.commit;

import io.ehdev.conrad.database.model.project.commit.ApiFullCommitModel;
import io.ehdev.conrad.version.commit.internal.DefaultCommitVersion;

import java.util.Arrays;

public class VersionFactory {

    private final static int[] EMPTY_INT_ARRAY = new int[] {};

    public static CommitVersion parse(ApiFullCommitModel version) {
        if(null == version) {
            parse("0.0.0");
        } else {
            parse(version.getVersion());
        }
    }

    public static CommitVersion parse(String version) {
        String postfix = null;
        String numberString = version;
        if (version.contains("-")) {
            int indexOfDash = version.indexOf('-');
            postfix = version.substring(indexOfDash + 1);
            numberString = version.substring(0, indexOfDash);
        }

        String[] splitVersion = numberString.split("\\.");
        int[] versionList = Arrays.copyOf(EMPTY_INT_ARRAY, splitVersion.length);
        for (int i = 0; i < Math.min(splitVersion.length, versionList.length); i++) {
            versionList[i] = Integer.parseInt(splitVersion[i]);
        }

        return new DefaultCommitVersion(versionList, postfix);
    }

}
