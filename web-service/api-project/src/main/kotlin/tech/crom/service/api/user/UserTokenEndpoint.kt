package tech.crom.service.api.user

import io.ehdev.conrad.model.permission.CreateTokenResponse
import io.ehdev.conrad.model.permission.GetTokensResponse
import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import tech.crom.business.api.TokenManagementApi
import tech.crom.model.token.TokenType
import tech.crom.web.api.model.RequestDetails
import java.time.ZoneOffset
import java.time.ZonedDateTime


@Controller
@RequestMapping("/api/v1/user/tokens")
open class UserTokenEndpoint @Autowired constructor(
    val tokenManagementApi: TokenManagementApi
) {

    @LoggedInUserRequired
    @RequestMapping(value = "/{tokenId}", method = arrayOf(RequestMethod.DELETE))
    open fun deleteToken(requestDetails: RequestDetails, @PathVariable("tokenId") tokenId: String): ResponseEntity<*> {
        tokenManagementApi.invalidateToken(tokenId.toLong(), TokenType.USER)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @LoggedInUserRequired
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    open fun createNewToken(requestDetails: RequestDetails,
                            @RequestParam(value = "validFor", required = false) validFor: Int?): ResponseEntity<CreateTokenResponse> {

        val now = ZonedDateTime.now(ZoneOffset.UTC)
        val expiresAt = if (validFor == null) now.plusDays(60) else now.plusDays(validFor.toLong())
        val token = tokenManagementApi.createToken(requestDetails.requestPermission.cromUser!!, expiresAt)

        val created = CreateTokenResponse(
            token.id,
            token.createdAt,
            token.expiresAt,
            token.value)

        return ResponseEntity(created, HttpStatus.CREATED)
    }

    @LoggedInUserRequired
    @RequestMapping(method = arrayOf(RequestMethod.GET))
    open fun findAllTokens(requestDetails: RequestDetails): ResponseEntity<GetTokensResponse> {
        val tokens = tokenManagementApi.getTokens(requestDetails.requestPermission.cromUser!!)
            .map({ it -> GetTokensResponse.TokenEntryModel(it.id, it.createdAt, it.expiresAt) })
            .toList()

        return ResponseEntity(GetTokensResponse(tokens), HttpStatus.OK)
    }
}
