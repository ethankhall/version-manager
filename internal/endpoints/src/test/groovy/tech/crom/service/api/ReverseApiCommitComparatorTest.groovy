package tech.crom.service.api

import spock.lang.Specification
import tech.crom.model.commit.impl.RealizedCommit

class ReverseApiCommitComparatorTest extends Specification {

    def 'can compare commits'() {
        expect:
        new ReverseApiCommitComparator().compare(
            RealizedCommit.createNewCommit('', '1.2.3', null),
            RealizedCommit.createNewCommit('', '1.2.3', null)) == 0
        new ReverseApiCommitComparator().compare(
            RealizedCommit.createNewCommit('', '1.2.3', null),
            RealizedCommit.createNewCommit('', '1.2.4', null)) == 1
        new ReverseApiCommitComparator().compare(
            RealizedCommit.createNewCommit('', '1.2.3', null),
            RealizedCommit.createNewCommit('', '1.2.3.1', null)) == 1
        new ReverseApiCommitComparator().compare(
            RealizedCommit.createNewCommit('', '1.2.4', null),
            RealizedCommit.createNewCommit('', '1.2.3.1', null)) == -1
        new ReverseApiCommitComparator().compare(
            RealizedCommit.createNewCommit('', '1.2.9', null),
            RealizedCommit.createNewCommit('', '1.2.11', null)) == 1
        new ReverseApiCommitComparator().compare(
            RealizedCommit.createNewCommit('', '1.2.4', null),
            RealizedCommit.createNewCommit('', '1.2.3', null)) == -1
    }
}
