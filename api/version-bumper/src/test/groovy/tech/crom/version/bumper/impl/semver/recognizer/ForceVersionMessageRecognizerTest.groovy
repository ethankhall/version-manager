package tech.crom.version.bumper.impl.semver.recognizer

import de.svenjacobs.loremipsum.LoremIpsum
import spock.lang.Specification
import spock.lang.Unroll

import static tech.crom.version.bumper.impl.TestUtils.createVersionDetails

class ForceVersionMessageRecognizerTest extends Specification {

    def ipsum = new LoremIpsum()

    @Unroll
    def 'versions that should be forced : #name'() {
        when:
        def forceBody = "[force version $version]"
        def message = surround ? ipsum.getParagraphs(2) + forceBody + ipsum.getParagraphs(2) : forceBody
        def recognizer = new ForceVersionMessageRecognizer().produce(createVersionDetails(), message)

        then:
        assert recognizer
        version == recognizer.nextVersion()

        where:
        name                                 | version                   | surround
        'should work with short message'     | '1.2.4'                   | false
        'should search with full message'    | '1.2.4'                   | true
        'should parse a long version string' | '1.2.3.4.5.6.7.8.9-BETA2' | true
    }
}
