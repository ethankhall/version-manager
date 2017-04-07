package tech.crom.version.bumper.impl.semver.recognizer

import de.svenjacobs.loremipsum.LoremIpsum
import spock.lang.Specification
import spock.lang.Unroll

import static tech.crom.version.bumper.impl.TestUtils.createVersionDetails

class WildcardMessageRecognizerTest extends Specification {

    @Unroll
    def 'a new version will be used : will produce a #name version bump'() {
        when:
        def recognizer = new WildcardMessageRecognizer()
        def versionCreator = recognizer.produce(createVersionDetails(), generateMessage(partNumber))

        then:
        assert versionCreator
        partNumber == versionCreator.groupNumber
        expectedVersion == versionCreator.nextVersion()

        where:
        name    | partNumber | expectedVersion
        'major' | 0          | '2.0.0'
        'minor' | 1          | '1.3.0'
        'patch' | 2          | '1.2.4'
        'build' | 3          | '1.2.3.1'
        'other' | 99         | '1.2.3' + '.0' * 96 + '.1'
    }

    String generateMessage(int input) {
        def loremIpsum = new LoremIpsum()
        return loremIpsum.getParagraphs(3) + "[bump group $input]" + loremIpsum.getParagraphs(2)
    }
}
