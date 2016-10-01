package tech.crom.data.gen

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.httpPost
import java.util.*

fun main(args : Array<String>) {
    if(args.size < 2) {
        throw RuntimeException("Must have provide [api] [token] on the commandline")
    }

    val apiBase = args[0]
    val token = args[1]

    val projectName = UUID.randomUUID().toString()
    val repoName = UUID.randomUUID().toString()

    val body = jacksonObjectMapper().writeValueAsString(
        mapOf(Pair("scmUrl", "http://localhost/$projectName"), Pair("bumper", "semver")))

    apiBase + "/api/v1/project".httpPost().header(Pair("X-AUTH-TOKEN", token)).body(body).response { request, response, result ->  }

}
