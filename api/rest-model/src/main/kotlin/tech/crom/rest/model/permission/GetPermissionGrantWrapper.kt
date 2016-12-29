package tech.crom.rest.model.permission

import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import com.fasterxml.jackson.annotation.JsonTypeName
import tech.crom.rest.model.OutputModel

@OutputModel
@JsonClassDescription("A list containing the permissions for a resource")
@JsonTypeName("PermissionWrapper")
data class GetPermissionGrantWrapper(
    @JsonPropertyDescription("A list containing all the permissions.")
    @JsonProperty("permissions")
    val permissions: List<PermissionGrant>
)
