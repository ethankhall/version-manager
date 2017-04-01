package tech.crom.rest.model.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

class CreateHistoryTest extends Specification {

    def om = new ObjectMapper().registerModules(new KotlinModule(), new JavaTimeModule())

    @Unroll
    def 'will handle #name time'() {
        setup:
        def map = [scmUrl : 'git@github.com:foo/bar.git',
                   bumper : 'semver',
                   history: [
                       [commitId: 0, version: '0.0.1'],
                       [commitId: 1, version: '1.0.1', createdAt: timeString],
                   ]
        ]

        def s = om.writeValueAsString(map)
        def createRepoRequest = om.readValue(s, CreateRepoRequest)
        def date = LocalDate.of(2016, 9, 10)
        def time = LocalTime.of(17, 15, 30).with(ChronoField.MILLI_OF_SECOND, 545L)
        def comparator = Comparator.comparing({ zdt -> zdt.truncatedTo(ChronoUnit.NANOS) })

        expect:
        createRepoRequest.history.last().createdAt != null
        comparator.compare(ZonedDateTime.of(date, time, zone), createRepoRequest.history.last().createdAt)

        where:
        name        | timeString                      | zone
        "Zulu"      | '2016-09-10T17:15:30.545Z'      | ZoneOffset.UTC
        'offset -8' | '2016-09-10T17:15:30.545-08:00' | ZoneOffset.ofHours(-8)
    }
}
