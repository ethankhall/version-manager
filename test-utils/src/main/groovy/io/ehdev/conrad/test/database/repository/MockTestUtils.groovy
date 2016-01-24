package io.ehdev.conrad.test.database.repository;

import io.ehdev.conrad.backend.version.commit.VersionFactory;
import io.ehdev.conrad.database.impl.CommitModel;
import org.apache.commons.lang3.RandomStringUtils;

public class MockTestUtils {

    static CommitModel createCommit(String version, CommitModel parent= null){
        return new CommitModel(RandomStringUtils.randomAlphanumeric(40), null, VersionFactory.parse(version), parent)
    }
}
