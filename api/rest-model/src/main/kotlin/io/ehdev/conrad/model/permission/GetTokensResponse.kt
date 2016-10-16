package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime
import java.util.*


@JsonInclude(JsonInclude.Include.NON_EMPTY)
class GetTokensResponse(
    @JsonProperty("tokens") val entryList: List<TokenEntryModel>) {

    class TokenEntryModel(@JsonProperty("id") val id: UUID,
                          @JsonProperty("createdAt") val createdAt: ZonedDateTime,
                          @JsonProperty("expiresAt") val expiresAt: ZonedDateTime)
}
