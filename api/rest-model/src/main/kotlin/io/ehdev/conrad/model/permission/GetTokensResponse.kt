package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime


@JsonInclude(JsonInclude.Include.NON_EMPTY)
class GetTokensResponse(
    @JsonProperty("tokens") val entryList: List<TokenEntryModel>) {

    class TokenEntryModel(@JsonProperty("id") val id: String,
                          @JsonProperty("createdAt") val createdAt: ZonedDateTime,
                          @JsonProperty("expiresAt") val expiresAt: ZonedDateTime)
}
