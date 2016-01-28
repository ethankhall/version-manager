package io.ehdev.conrad.model.rest

import spock.lang.Specification
import spock.lang.Unroll

class RestCommitModelTest extends Specification {

//    @Unroll
//    def 'can create repository version with #version'() {
//        expect:
//        VersionFactory.parse(version) == expected
//
//        where:
//        version          | expected
//        "1"              | new RestCommitModel(1, 0, 0)
//        "1.2"            | new RestCommitModel(1, 2, 0)
//        "1.2.3"          | new RestCommitModel(1, 2, 3)
//        "1.2.3.4"        | new RestCommitModel([1, 2, 3, 4] as int[], null)
//        "1.2.3-SNAPSHOT" | new DefaultCommitVersion(1, 2, 3, 'SNAPSHOT')
//        "1-SNAPSHOT"     | new RestCommitModel(1, 0, 0, 'SNAPSHOT')
//    }

    @Unroll
    def 'parsing #input'() {
        setup:
        def version = new RestCommitModel('1', input)

        expect:
        version.versionParts.size() == parts.size()
        parts.eachWithIndex { int entry, int index ->
            assert version.versionParts[index] == entry
        }
        version.postfix == postfix
        version.toString() == input

        where:
        input        | parts     | postfix
        '1.2.3-BETA' | [1, 2, 3] | 'BETA'
        '1'          | [1]       | null
        '1.2'        | [1, 2]    | null
        '1.2.3'      | [1, 2, 3] | null
        '1-SNAPSHOT' | [1]       | "SNAPSHOT"
    }
}
