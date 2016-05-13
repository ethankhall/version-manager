package io.ehdev.conrad.database.api.internal.v2;

import io.ehdev.conrad.database.api.v2.PermissionManagementApiV2;
import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceId;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.daos.UserDetailsDao;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PermissionManagementApiV2Impl implements PermissionManagementApiV2 {

    private final DSLContext dslContext;
    private final UserDetailsDao userDetailsDao;

    @Autowired
    public PermissionManagementApiV2Impl(DSLContext dslContext,
                                          UserDetailsDao userDetailsDao) {
        this.dslContext = dslContext;
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public ApiUserPermission findHighestUserPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) {
        if(authUserDetails == null) {
            return ApiUserPermission.NONE;
        }

        UserPermissionsTable up = Tables.USER_PERMISSIONS.as("up");
        //@formatter:off
        Record record = dslContext
            .select()
            .from(up)
            .where(up.PROJECT_UUID.eq(resourceDetails.getProjectId().getId()))
                .and(repoOrNull(up, resourceDetails))
                .and(up.USER_UUID.eq(authUserDetails.getUserId()))
            .orderBy(up.PERMISSIONS.desc())
            .limit(1)
            .fetchOne();
        //@formatter:on

        if(record == null) {
            return ApiUserPermission.NONE;
        }

        UserPermissionsRecord userPermissionsRecord = record.into(UserPermissionsRecord.class);
        return ApiUserPermission.findById(userPermissionsRecord.getPermissions());
    }

    private Condition repoOrNull(UserPermissionsTable up, ResourceDetails resourceDetails) {
        Condition condition = up.REPO_DETAILS_UUID.isNull();
        if(resourceDetails.getRepoId() != null) {
            condition = condition.or(up.REPO_DETAILS_UUID.eq(resourceDetails.getRepoId().getId()));
        }
        return condition;
    }

    @Override
    public boolean addPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails, ApiUserPermission permission) {

        UserDetails userDetails = userDetailsDao.fetchOneByUuid(authUserDetails.getUserId());

        if (userDetails == null) {
            throw new RuntimeException(); //TODO make this more specific
        }

        ResourceId projectId = resourceDetails.getProjectId();
        ResourceId repoId = resourceDetails.getRepoId();
        if(repoId != null) {
            doInsert(projectId.getName(), projectId.getId(), repoId.getName(), repoId.getId(), userDetails, permission);
        } else {
            doInsert(projectId.getName(), projectId.getId(), null, null, userDetails, permission);
        }
        return true;
    }

    @Override
    public List<ApiUserPermissionDetails> getPermissions(ResourceDetails resourceDetails) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS;

        //@formatter:off
        Result<Record> fetch = dslContext
            .select()
            .from(up)
            .where(up.PROJECT_UUID.eq(resourceDetails.getProjectId().getId()))
                .and(repoOrNull(up, resourceDetails))
            .fetch();
        //@formatter:on

        return fetch.into(UserPermissionsRecord.class).stream().map(this::convert).collect(Collectors.toList());
    }

    private ApiUserPermissionDetails convert(UserPermissionsRecord record) {
        UserDetails userDetails = userDetailsDao.fetchOneByUuid(record.getUserUuid());
        return new ApiUserPermissionDetails(userDetails.getUserName(), ApiUserPermission.findById(record.getPermissions()).name());
    }

    private void doInsert(String projectName, UUID projectUUID, String repoName, UUID repoId, UserDetails userDetails, ApiUserPermission permission) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS;

        //@formatter:off
        Record1<UUID> record = dslContext
            .select(up.UUID)
            .from(up)
            .where(up.PROJECT_UUID.eq(projectUUID))
                .and(repoId != null ? up.REPO_DETAILS_UUID.eq(repoId) : up.REPO_DETAILS_UUID.isNull())
                .and(up.USER_UUID.eq(userDetails.getUuid()))
            .fetchOne();
        //@formatter:on

        if(record == null && ApiUserPermission.NONE != permission) {
            dslContext
                .insertInto(up)
                .columns(up.PROJECT_NAME, up.PROJECT_UUID, up.REPO_NAME, up.REPO_DETAILS_UUID, up.USER_UUID, up.PERMISSIONS)
                .values(projectName, projectUUID, repoName, repoId, userDetails.getUuid(), permission.getSecurityId())
                .execute();
        } else if(record != null) {
            if(ApiUserPermission.NONE == permission) {
                dslContext
                    .delete(up)
                    .where(up.UUID.eq(record.value1()))
                    .execute();
            } else {
                dslContext
                    .update(up)
                    .set(up.PERMISSIONS, permission.getSecurityId())
                    .where(up.UUID.eq(record.value1()))
                    .execute();
            }
        }
    }
}
