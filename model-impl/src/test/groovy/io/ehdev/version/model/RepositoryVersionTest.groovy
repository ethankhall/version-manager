package io.ehdev.version.model

import spock.lang.Specification
import spock.lang.Unroll

class RepositoryVersionTest extends Specification {

    @Unroll
    def 'can create repository version with #version'() {
        expect:
        RepositoryVersion.parse(version) == expected

        where:
        version          | expected
        "1"              | new RepositoryVersion(1, 0, 0)
        "1.2"            | new RepositoryVersion(1, 2, 0)
        "1.2.3"          | new RepositoryVersion(1, 2, 3)
        "1.2.3.4"        | new RepositoryVersion(1, 2, 3)
        "1.2.3-SNAPSHOT" | new RepositoryVersion(1, 2, 3, 'SNAPSHOT')
        "1-SNAPSHOT"     | new RepositoryVersion(1, 0, 0, 'SNAPSHOT')
    }

    def 'can bump to snapshot'() {
        expect:
        new RepositoryVersion(1, 0, 0).bumpPatch().toString() == '1.0.1'
        new RepositoryVersion(1, 0, 0).asSnapshot().toString() == '1.0.0-SNAPSHOT'
        new RepositoryVersion(1, 0, 1).bumpMinor().toString() == '1.1.0'
        new RepositoryVersion(1, 1, 1).bumpMajor().toString() == '2.0.0'
        new RepositoryVersion(1, 1, 1).withPostfix("ALPHA1").toString() == '1.1.1-ALPHA1'
    }

    def 'parsing check'() {
        setup:
        def version = RepositoryVersion.parse("1.2.3-BETA")

        expect:
        version.getMajor() == 1
        version.getMinor() == 2
        version.getPatch() == 3
        version.getPostFix() == 'BETA'
    }
}
