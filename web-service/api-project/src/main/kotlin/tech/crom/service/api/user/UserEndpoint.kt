package tech.crom.service.api.user

import io.ehdev.conrad.model.user.GetFullProfileResponse
import io.ehdev.conrad.model.user.GetWatchesResponse
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.aop.annotation.ProjectRequired
import io.ehdev.conrad.service.api.aop.annotation.RepoRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.UserApi
import tech.crom.business.api.WatcherApi
import tech.crom.web.api.model.RequestDetails


@Controller
@RequestMapping("/api/v1/user")
open class UserEndpoint @Autowired constructor(
    val userApi: UserApi,
    val watcherApi: WatcherApi
) {

    @LoggedInUserRequired
    @RequestMapping(value = "/profile")
    open fun findProfile(requestDetails: RequestDetails): ResponseEntity<GetFullProfileResponse> {
        val user = userApi.findUserDetails(requestDetails.requestPermission.cromUser!!.userUid)!!

        return ResponseEntity(GetFullProfileResponse(user.userName, null, user.displayName), HttpStatus.OK)
    }

    @ProjectRequired
    @LoggedInUserRequired
    @RequestMapping(value = "/watch/project/{projectName}", method = arrayOf(RequestMethod.POST))
    open fun watchProject(requestDetails: RequestDetails): ResponseEntity<Any> {
        watcherApi.addWatch(requestDetails.requestPermission.cromUser!!, requestDetails.cromProject!!)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @RepoRequired
    @LoggedInUserRequired
    @RequestMapping(value = "/watch/project/{projectName}/repo/{repoName}", method = arrayOf(RequestMethod.POST))
    open fun watchRepo(requestDetails: RequestDetails): ResponseEntity<Any> {
        watcherApi.addWatch(requestDetails.requestPermission.cromUser!!, requestDetails.cromRepo!!)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @LoggedInUserRequired
    @RequestMapping(value = "/watches")
    open fun getWatches(requestDetails: RequestDetails): ResponseEntity<GetWatchesResponse> {
        val watches = watcherApi.getWatches(requestDetails.requestPermission.cromUser!!)
        val details = watches.map { GetWatchesResponse.WatchDetails(it.cromProject.projectName, it.cromRepo?.repoName) }
        return ResponseEntity.ok(GetWatchesResponse(details))
    }
}
