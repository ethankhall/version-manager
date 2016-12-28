package io.ehdev.conrad.model.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import kotlin.test.assertNotNull

class CreateHistoryTest : Spek({
    given("a json blob") {
        val om = ObjectMapper().registerModules(KotlinModule(), JavaTimeModule())
        it("will handle Zulu time") {
            val map = mapOf(Pair("scmUrl", "git@github.com:foo/bar.git"),
                Pair("bumper", "semver"),
                Pair("history", arrayOf(
                    mapOf(Pair("commitId", "0"), Pair("version", "0.0.1")),
                    mapOf(Pair("commitId", "1"), Pair("version", "1.0.1"), Pair("createdAt", "2016-09-10T17:15:30.545Z"))
                )))

            val s = om.writeValueAsString(map)

            val createRepoRequest = om.readValue(s, CreateRepoRequest::class.java)

            assertNotNull(createRepoRequest.history!!.last().createdAt)
        }

        it("will handle with offset zone") {
            val map = mapOf(Pair("scmUrl", "git@github.com:foo/bar.git"),
                Pair("bumper", "semver"),
                Pair("history", arrayOf(
                    mapOf(Pair("commitId", "0"), Pair("version", "0.0.1")),
                    mapOf(Pair("commitId", "1"), Pair("version", "1.0.1"), Pair("createdAt", "2016-09-10T17:15:30.545-08:00"))
                )))

            val s = om.writeValueAsString(map)

            val createRepoRequest = om.readValue(s, CreateRepoRequest::class.java)

            assertNotNull(createRepoRequest.history!!.last().createdAt)
        }
    }
})
