package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.PrimaryKeySearchApi;
import io.ehdev.conrad.database.model.PrimaryResourceData;
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceId;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.database.model.user.ApiUserPermissionDetails;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.daos.UserDetailsDao;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultPermissionManagementApi implements PermissionManagementApi {

    private final DSLContext dslContext;
    private final UserDetailsDao userDetailsDao;
    private final PrimaryKeySearchApi primaryKeySearchApi;

    @Autowired
    public DefaultPermissionManagementApi(DSLContext dslContext,
                                          UserDetailsDao userDetailsDao,
                                          PrimaryKeySearchApi primaryKeySearchApi) {
        this.dslContext = dslContext;
        this.userDetailsDao = userDetailsDao;
        this.primaryKeySearchApi = primaryKeySearchApi;
    }

    @Override
    public ApiUserPermission findHighestUserPermission(UUID userId, ResourceDetails resourceDetails) {
        if(userId == null) {
            return ApiUserPermission.NONE;
        }

        UserPermissionsTable up = Tables.USER_PERMISSIONS.as("up");
        //@formatter:off
        Record record = dslContext
            .select()
            .from(up)
            .where(up.PROJECT_UUID.eq(resourceDetails.getProjectId().getId()))
                .and(repoOrNull(up, resourceDetails))
                .and(up.USER_UUID.eq(userId))
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

    @Override
    public ApiUserPermission findHighestUserPermission(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) {
        return findHighestUserPermission(authUserDetails.getUserId(), resourceDetails);
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

        return addPermission(resourceDetails, permission, userDetails);
    }

    @Override
    public boolean addPermission(String userName, ResourceDetails resourceDetails, ApiUserPermission permission) {
        UserDetails userDetails = userDetailsDao.fetchOneByUserName(userName);

        return addPermission(resourceDetails, permission, userDetails);
    }

    private boolean addPermission(ResourceDetails resourceDetails, ApiUserPermission permission, UserDetails userDetails) {
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

    @Override
    public UserPermissionGrants getPermissions(AuthUserDetails authUserDetails) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS;

        //@formatter:off
        List<UserPermissionsRecord> fetch = dslContext
            .select()
            .from(up)
            .where(up.USER_UUID.eq(authUserDetails.getUserId()))
            .fetch()
            .into(UserPermissionsRecord.class);
        //@formatter:on

        List<UserPermissionGrants.ProjectPermissionDetails> projectPermissionDetails = new ArrayList<>();
        List<UserPermissionGrants.RepoPermissionDetails> repoPermissionDetails = new ArrayList<>();

        fetch.forEach(it -> {
            if(it.getRepoDetailsUuid() == null) {
                Optional<PrimaryResourceData> optional = primaryKeySearchApi.findResourceDataByProjectId(it.getProjectUuid());
                String projectName = optional.get().getProjectName();
                projectPermissionDetails.add(new UserPermissionGrants.ProjectPermissionDetails(projectName, authUserDetails.getPermission()));
            } else {
                Optional<PrimaryResourceData> optional = primaryKeySearchApi.findResourceDataByRepoId(it.getRepoDetailsUuid());
                String projectName = optional.get().getProjectName();
                String repoName = optional.get().getRepoName();
                repoPermissionDetails.add(new UserPermissionGrants.RepoPermissionDetails(projectName, repoName, authUserDetails.getPermission()));
            }
        });
        return new UserPermissionGrants(projectPermissionDetails, repoPermissionDetails);
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
                .columns(up.PROJECT_UUID, up.REPO_DETAILS_UUID, up.USER_UUID, up.PERMISSIONS)
                .values(projectUUID, repoId, userDetails.getUuid(), permission.getSecurityId())
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
