package io.ehdev.conrad.database.model.comparator

import io.ehdev.conrad.database.model.project.commit.ApiCommitModel
import spock.lang.Specification

class ReverseApiCommitComparatorTest extends Specification {

    def 'can parse versions'() {
        def comparator = new ReverseApiCommitComparator()

        expect:
        comparator.compare(new ApiCommitModel('1', '1.0.0', null), new ApiCommitModel('2','1.0.0', null)) == 0
    }

    def 'can order versions'() {
        def list = [
            new ApiCommitModel('1', '1.2.3', null),
            new ApiCommitModel('1', '1.2.3.1', null),
            new ApiCommitModel('1', '5.0.0', null),
            new ApiCommitModel('1', '1.3.0', null),
            new ApiCommitModel('1', '1.0.0', null),
            new ApiCommitModel('1', '2', null),
        ]

        def comparator = new ReverseApiCommitComparator()

        when:
        list.sort(comparator)

        then:
        list[0].version == '5.0.0'
        list[1].version == '2'
        list[2].version == '1.3.0'
        list[3].version == '1.2.3.1'
        list[4].version == '1.2.3'
        list[5].version == '1.0.0'
    }

    def 'test list'() {
        def list = [
            new ApiCommitModel('1', '1.2.3', null),
            new ApiCommitModel('2', '1.3.4', null),
        ]

        def comparator = new ReverseApiCommitComparator()

        when:
        list.sort(comparator)

        then:
        list[0].version == '1.3.4'
        list[1].version == '1.2.3'
    }

    def 'finish out test coverage'() {
        def same = new ApiCommitModel('1', '1.2.3', null)
        def comparator = new ReverseApiCommitComparator()

        expect:
        comparator.compare(same, same) == 0
    }
}
