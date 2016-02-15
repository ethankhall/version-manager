package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.model.user.ApiUser;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.daos.UserDetailsDao;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
import io.ehdev.conrad.db.tables.pojos.RepoDetails;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPermissionManagementApi implements PermissionManagementApiInternal {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPermissionManagementApi.class);

    private final DSLContext dslContext;
    private final UserDetailsDao userDetailsDao;
    private final RepoManagementApiInternal repoManagementApi;
    private final ProjectManagementApiInternal projectManagementApi;

    @Autowired
    public DefaultPermissionManagementApi(DSLContext dslContext,
                                          UserDetailsDao userDetailsDao,
                                          RepoManagementApiInternal repoManagementApi,
                                          ProjectManagementApiInternal projectManagementApi) {
        this.dslContext = dslContext;
        this.userDetailsDao = userDetailsDao;
        this.repoManagementApi = repoManagementApi;
        this.projectManagementApi = projectManagementApi;
    }

    @Override
    public boolean doesUserHavePermission(ApiUser apiUser, String project, String repoName, ApiUserPermission permission) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS.as("up");
        //@formatter:off
        int count = dslContext
            .selectCount()
            .from(up)
            .where(up.PROJECT_NAME.eq(project))
                .and(up.REPO_NAME.eq(repoName).or(up.REPO_NAME.isNull()))
                .and(up.USER_UUID.eq(apiUser.getUuid()))
                .and(up.PERMISSIONS.greaterOrEqual(permission.getSecurityId()))
            .fetchOne()
            .value1();
        //@formatter:on

        return count != 0;
    }
    @Override
    public boolean addPermission(String username, ApiUser authenticatedUser, String projectName, String repoName, ApiUserPermission permission) {
        if (!doesUserHavePermission(authenticatedUser, projectName, repoName, ApiUserPermission.ADMIN)) {
            return false;
        }

        return forceAddPermission(username, projectName, repoName, permission);
    }

    public boolean forceAddPermission(String username, String projectName, String repoName, ApiUserPermission permission) {
        UserDetails userDetails = userDetailsDao.fetchOneByUserName(username);

        if (userDetails == null) {
            throw new RuntimeException(); //TODO make this more specific
        }

        if(repoName != null) {
            Optional<RepoDetails> details = repoManagementApi.findRepository(projectName, repoName);
            RepoDetails repoDetails = details.get();
            doInsert(repoDetails.getProjectName(), repoDetails.getProjectUuid(), repoDetails.getRepoName(), repoDetails.getUuid(), userDetails, permission);
        } else {
            ProjectDetails details = projectManagementApi.findProject(projectName).get();
            doInsert(details.getProjectName(), details.getUuid(), null, null, userDetails, permission);
        }
        return true;
    }

    private void doInsert(String projectName, UUID projectUUID, String repoName, UUID repoId, UserDetails userDetails, ApiUserPermission permission) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS;

        //@formatter:off
        Record1<UUID> record = dslContext
            .select(up.UUID)
            .from(up)
            .where(up.PROJECT_UUID.eq(projectUUID))
                .and(up.REPO_DETAILS_UUID.eq(repoId))
                .and(up.USER_UUID.eq(userDetails.getUuid()))
            .fetchOne();
        //@formatter:on

        if(record == null) {
            dslContext
                .insertInto(up)
                .columns(up.PROJECT_NAME, up.PROJECT_UUID, up.REPO_NAME, up.REPO_DETAILS_UUID, up.USER_UUID, up.PERMISSIONS)
                .values(projectName, projectUUID, repoName, repoId, userDetails.getUuid(), permission.getSecurityId())
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
