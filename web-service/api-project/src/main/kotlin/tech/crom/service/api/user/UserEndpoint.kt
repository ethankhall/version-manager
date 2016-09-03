package tech.crom.service.api.user

import io.ehdev.conrad.model.user.GetFullProfileResponse
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import tech.crom.business.api.UserApi
import tech.crom.web.api.model.RequestDetails


@Controller
@RequestMapping("/api/v1/user")
open class UserEndpoint @Autowired constructor(
    val userApi: UserApi
){

    @LoggedInUserRequired
    @RequestMapping(value = "/profile")
    open fun findProfile(requestDetails: RequestDetails): ResponseEntity<GetFullProfileResponse> {
        val user = userApi.findUserDetails(requestDetails.requestPermission.cromUser!!.userUid)!!

        return ResponseEntity(GetFullProfileResponse(user.userName, null, user.displayName), HttpStatus.OK)
    }
}
