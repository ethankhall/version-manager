package io.ehdev.conrad.model.rest

class RestVersionCollectionModel(val commits: List<RestCommitModel>, val latest: RestCommitModel? = null)
