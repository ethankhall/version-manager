package tech.crom.service.api.user

import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.WatcherApi
import tech.crom.rest.model.user.GetWatchesResponse
import tech.crom.web.api.model.RequestDetails

@Controller
@RequestMapping("/api/v1/user")
class UserWatchEndpoint @Autowired constructor(
    val watcherApi: WatcherApi
) {

    @ProjectRequired
    @LoggedInUserRequired
    @RequestMapping(value = ["/watch/project/{projectName}"], method = [RequestMethod.POST])
    fun watchProject(requestDetails: RequestDetails): ResponseEntity<Any> {
        watcherApi.addWatch(requestDetails.requestPermission.cromUser!!, requestDetails.cromProject!!)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @RepoRequired
    @LoggedInUserRequired
    @RequestMapping(value = ["/watch/project/{projectName}/repo/{repoName}"], method = [RequestMethod.POST])
    fun watchRepo(requestDetails: RequestDetails): ResponseEntity<Any> {
        watcherApi.addWatch(requestDetails.requestPermission.cromUser!!, requestDetails.cromRepo!!)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @LoggedInUserRequired
    @RequestMapping(value = ["/watch"], method = [RequestMethod.GET])
    fun getWatches(requestDetails: RequestDetails): ResponseEntity<GetWatchesResponse> {
        val watches = watcherApi.getWatches(requestDetails.requestPermission.cromUser!!)
        val details = watches.map { GetWatchesResponse.WatchDetails(it.cromProject.projectName, it.cromRepo?.repoName) }
        return ResponseEntity.ok(GetWatchesResponse(details))
    }
}
