package io.ehdev.conrad.client.java

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

open class VersionServiceConfiguration(val client: OkHttpClient) {
    var repoName: String by Delegates.notNull()
    var projectName: String by Delegates.notNull()
    var applicationUrl: String by Delegates.notNull()

    constructor() : this(OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build()) { }
}
