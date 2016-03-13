package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.DefaultResourceSupport
import java.time.ZonedDateTime
import java.util.*


class GetTokensResponse(
    @JsonProperty("tokens") val entryList: List<TokenEntryModel>) : DefaultResourceSupport() {

    class TokenEntryModel(@JsonProperty("id") val id: UUID,
                          @JsonProperty("createdAt") val createdAt: ZonedDateTime,
                          @JsonProperty("expiresAt") val expiresAt: ZonedDateTime)
}
