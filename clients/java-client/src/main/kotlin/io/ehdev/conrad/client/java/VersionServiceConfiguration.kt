package io.ehdev.conrad.client.java

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

open class VersionServiceConfiguration(val client: OkHttpClient) {
    var repoName: String? = null
    var projectName: String? = null
    var applicationUrl: String? = null
    var authToken: String? = null

    constructor() : this(OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build()) { }
}
