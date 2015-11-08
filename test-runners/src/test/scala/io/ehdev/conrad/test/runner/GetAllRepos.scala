package io.ehdev.conrad.test.runner

import io.gatling.core.Predef._
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GetAllRepos extends Simulation {

    val conf = http.baseURL("http://172.0.1.100:8080")

    //feeders
    // curl http://172.0.1.100:8080/api/repo | jq  -r '. | keys[] | [.] | @csv' | sed 's/"//g' > /Volumes/Workspace/version-manager/test-runners/src/test/resources/repos.csv
    private val csvFeeder: RecordSeqFeederBuilder[String] = csv("repos.csv").random

    val version = scenario("Get version info for repo")
    .feed(csvFeeder)
    .during(60 seconds) {
        exec(http("version").get("/api/version/${repoId}"))
    }

    setUp(version.inject(constantUsersPerSec(10) during(10 seconds))).protocols(conf)
}
