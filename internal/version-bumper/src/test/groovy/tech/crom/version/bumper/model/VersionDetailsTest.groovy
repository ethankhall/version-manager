package tech.crom.version.bumper.model

import spock.lang.Specification
import spock.lang.Unroll
import tech.crom.model.commit.VersionDetails

class VersionDetailsTest extends Specification {

    @Unroll
    def 'will be able to parse : #name'() {
        when:
        def normalVersion = new VersionDetails(versionString)

        then:
        postFix == normalVersion.postFix
        versionString == normalVersion.versionString
        versionParts == normalVersion.versionParts

        where:
        name                           | versionString            | postFix            | versionParts
        'normal version'               | '1.2.3'                  | null               | [1, 2, 3]
        'single number version'        | '1'                      | null               | [1]
        'long normal version'          | '1.2.3.4.5.6.7.8.9'      | null               | [1, 2, 3, 4, 5, 6, 7, 8, 9]
        'version with postfix'         | '1.2.3-SNAPSHOT'         | 'SNAPSHOT'         | [1, 2, 3]
        'spring version'               | '1.2.3.RELEASE'          | 'RELEASE'          | [1, 2, 3]
        'spring version with snapshot' | '1.2.3.RELEASE-SNAPSHOT' | 'RELEASE-SNAPSHOT' | [1, 2, 3]
    }

    def 'will throw when only a snapshot is used'() {
        when:
        new VersionDetails("SNAPSHOT")

        then:
        def e = thrown(RuntimeException)
        'Must contain at least one number' == e.message
    }
}
