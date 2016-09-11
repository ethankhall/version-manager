package tech.crom.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class LoadTestSimulation extends Simulation {

    val httpConf = http.baseURL("http://localhost:8080")

    val projectDetails = scenario("GetProjectDetails").repeat(300) {
        exec(http("request_1").get("/api/v1/project/ethankhall").check(status.is(200)))
    }

    val repoDetails = scenario("GetRepoDetails").repeat(300) {
        exec(http("request_2").get("/api/v1/project/ethankhall/repo/version-manager").check(status.is(200)))
    }

    val repoVersions = scenario("GetVersions").repeat(300) {
        exec(http("request_3").get("/api/v1/project/ethankhall/repo/version-manager/versions").check(status.is(200)))
    }

    setUp(
        projectDetails.inject(rampUsers(43).over(3 seconds)),
        repoDetails.inject(rampUsers(37).over(5 seconds)),
        repoVersions.inject(rampUsers(33).over(7 seconds))
    ).protocols(httpConf)
}
