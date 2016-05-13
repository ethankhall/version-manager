package io.ehdev.conrad.database.api.internal.v2;

import io.ehdev.conrad.database.api.v2.UserManagementV2;
import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.permission.UserApiAuthentication;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.database.model.user.UserPermissionGrants;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserManagementV2Impl implements UserManagementV2 {

    private final DSLContext dslContext;

    @Autowired
    public UserManagementV2Impl(DSLContext dslContext) {
        this.dslContext = dslContext;
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

        records.forEach( it -> {
            if (StringUtils.isBlank(it.getRepoName())) {
                projectPermissions.add(new UserPermissionGrants.ProjectPermissionDetails(it.getProjectName(),
                    ApiUserPermission.findById(it.getPermissions())));
            } else {
                repoPermissions.add(new UserPermissionGrants.RepoPermissionDetails(it.getProjectName(), it.getRepoName(),
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
