package tech.crom.version.bumper.impl.semver.recognizer

import de.svenjacobs.loremipsum.LoremIpsum
import spock.lang.Specification
import spock.lang.Unroll

import static tech.crom.version.bumper.impl.TestUtils.createVersionDetails

class SquareBracketMessageRecognizerTest extends Specification {

    @Unroll
    def 'will parse square brackets : #name'() {
        when:
        def recognizer = new SquareBracketMessageRecognizer(name, part)

        then:
        expectedVersion == recognizer.produce(createVersionDetails(), generateMessage("[bump $name version]")).nextVersion()
        expectedVersion == recognizer.produce(createVersionDetails(), generateMessage("[bump $name version ]")).nextVersion()
        expectedVersion == recognizer.produce(createVersionDetails(), generateMessage("[ bump $name version]")).nextVersion()
        expectedVersion == recognizer.produce(createVersionDetails(), generateMessage("[ bump $name version ]")).nextVersion()

        where:
        name       | part | expectedVersion
        'major'    | 0    | '2.0.0'
        'minor'    | 1    | '1.3.0'
        'patch'    | 2    | '1.2.4'
        'build'    | 3    | '1.2.3.1'
    }

    def generateMessage(String message) {
        def loremIpsum = new LoremIpsum()
        return loremIpsum.getParagraphs(3) + message + loremIpsum.getParagraphs(2)
    }
}
