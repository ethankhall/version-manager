package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.PrimaryKeySearchApi;
import io.ehdev.conrad.database.api.UserManagementApi;
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.PrimaryResourceData;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserManagementApi implements UserManagementApi {

    private final DSLContext dslContext;
    private final PrimaryKeySearchApi primaryKeySearchApi;

    @Autowired
    public DefaultUserManagementApi(DSLContext dslContext, PrimaryKeySearchApi primaryKeySearchApi) {
        this.dslContext = dslContext;
        this.primaryKeySearchApi = primaryKeySearchApi;
    }

    @Override
    public UserPermissionGrants getUserPermissions(AuthUserDetails authUserDetails) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS;

        List<UserPermissionsRecord> records = dslContext.select()
            .from(up)
            .where(up.USER_UUID.eq(authUserDetails.getUserId()))
            .fetch()
            .into(UserPermissionsRecord.class);

        List<UserPermissionGrants.ProjectPermissionDetails> projectPermissions = new ArrayList<>();
        List<UserPermissionGrants.RepoPermissionDetails> repoPermissions = new ArrayList<>();

        records.forEach(it -> {
            if (it.getRepoDetailsUuid() == null) {
                Optional<PrimaryResourceData> projectData = primaryKeySearchApi.findResourceDataByProjectId(it.getProjectUuid());
                projectPermissions.add(new UserPermissionGrants.ProjectPermissionDetails(projectData.get().getProjectName(),
                    ApiUserPermission.findById(it.getPermissions())));
            } else {
                Optional<PrimaryResourceData> projectData = primaryKeySearchApi.findResourceDataByRepoId(it.getRepoDetailsUuid());
                PrimaryResourceData primaryResourceData = projectData.get();
                repoPermissions.add(new UserPermissionGrants.RepoPermissionDetails(
                    primaryResourceData.getProjectName(),
                    primaryResourceData.getRepoName(),
                    ApiUserPermission.findById(it.getPermissions())));
            }
        });

        return new UserPermissionGrants(projectPermissions, repoPermissions);
    }

    @Override
    public UserApiAuthentication createUser(String userName, String name, String email) {
        UserDetails userDetails = dslContext
            .insertInto(Tables.USER_DETAILS, Tables.USER_DETAILS.USER_NAME, Tables.USER_DETAILS.NAME, Tables.USER_DETAILS.EMAIL_ADDRESS)
            .values(userName, name, email)
            .returning(Tables.USER_DETAILS.fields())
            .fetchOne()
            .into(UserDetails.class);

        return ModelConversionUtility.toApiModel(userDetails);
    }
}
