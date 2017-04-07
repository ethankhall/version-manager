package io.ehdev.conrad.service.api.aop.impl

import org.jetbrains.annotations.NotNull
import tech.crom.model.project.CromProject
import tech.crom.model.repository.CromRepo
import tech.crom.model.security.authorization.AuthorizedObject
import tech.crom.model.security.authorization.CromPermission
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.api.PermissionService

class PermissionServiceTestDouble implements PermissionService {
    @Override
    void registerProject(@NotNull CromProject cromProject) {

    }

    @Override
    void registerRepository(@NotNull CromRepo cromRepo) {

    }

    @Override
    boolean hasAccessTo(@NotNull AuthorizedObject authorizedObject, @NotNull CromPermission accessLevel) {
        return false
    }

    @Override
    boolean hasAccessTo(@NotNull CromUser cromUser, @NotNull AuthorizedObject authorizedObject, @NotNull CromPermission accessLevel) {
        return false
    }

    @Override
    void grantPermission(@NotNull CromUser cromUser, @NotNull AuthorizedObject authorizedObject, @NotNull CromPermission accessLevel) {

    }

    @Override
    void revokePermission(@NotNull CromUser cromUser, @NotNull AuthorizedObject authorizedObject, @NotNull CromPermission accessLevel) {

    }

    @Override
    CromPermission findHighestPermission(@NotNull AuthorizedObject authorizedObject) {
        return CromPermission.ADMIN
    }

    @Override
    void remove(@NotNull AuthorizedObject authorizedObject) {

    }

    @Override
    List<PermissionService.PermissionPair> retrieveAllPermissions(@NotNull AuthorizedObject authorizedObject) {
        return null
    }
}
