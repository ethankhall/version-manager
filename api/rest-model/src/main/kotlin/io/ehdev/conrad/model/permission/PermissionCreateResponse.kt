package io.ehdev.conrad.model.permission

import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.conrad.model.ResourceLink
import io.ehdev.conrad.model.ResourceSupport

data class PermissionCreateResponse(@JsonProperty("accepted")
                                    val accepted: Boolean = false,

                                    @JsonProperty("links")
                                    override val links: List<ResourceLink>) : ResourceSupport
