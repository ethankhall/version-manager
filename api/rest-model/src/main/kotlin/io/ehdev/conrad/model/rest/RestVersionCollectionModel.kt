package io.ehdev.conrad.model.rest

import kotlin.collections.firstOrNull

class RestVersionCollectionModel(val commits: List<RestCommitModel>) {
    val latest = commits.firstOrNull()
}
