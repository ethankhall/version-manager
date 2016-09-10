package tech.crom.service.api.user

import io.ehdev.conrad.model.user.GetFullProfileResponse
import io.ehdev.conrad.model.user.PostUserUpdate
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import io.ehdev.conrad.service.api.exception.BaseHttpException
import io.ehdev.conrad.service.api.exception.ErrorCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tech.crom.business.api.UserApi
import tech.crom.model.user.CromUser
import tech.crom.web.api.model.RequestDetails


@Controller
@RequestMapping("/api/v1/user")
open class UserEndpoint @Autowired constructor(
    val userApi: UserApi
) {

    @LoggedInUserRequired
    @RequestMapping(value = "/profile")
    open fun findProfile(requestDetails: RequestDetails): ResponseEntity<GetFullProfileResponse> {
        val user = userApi.findUserDetails(requestDetails.requestPermission.cromUser!!.userUid)!!

        return ResponseEntity(GetFullProfileResponse(user.userName, null, user.displayName), HttpStatus.OK)
    }

    @LoggedInUserRequired
    @RequestMapping("/profile/update", method = arrayOf(RequestMethod.POST))
    open fun updateUser(requestDetails: RequestDetails, @RequestBody update: PostUserUpdate): ResponseEntity<Any> {

        var user = requestDetails.requestPermission.cromUser!!

        update.watches.forEach {
            val value = it.value.trim()
            if(value.length < 3) {
                throw BaseHttpException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.UPDATE_MUST_BE_LONGER, "`$value` must be at least 3 characters long.")
            }

            user = when(it.field) {
                PostUserUpdate.FieldDetails.DISPLAY_NAME -> user.copy(displayName = value)
                PostUserUpdate.FieldDetails.USER_NAME -> updateUserName(user, value)
            }
        }

        userApi.update(user)

        return ResponseEntity(HttpStatus.OK)
    }

    private fun updateUserName(user: CromUser, value: String): CromUser {
        if (user.userName != value) {
            if (userApi.findUserDetails(value) != null) {
                throw BaseHttpException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.USER_ALREADY_EXISTS, "Username already exists.")
            } else {
                return user.copy(userName = value)
            }
        }
        return user
    }
}
