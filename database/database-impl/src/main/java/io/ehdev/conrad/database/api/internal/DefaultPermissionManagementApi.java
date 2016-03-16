package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.permission.ApiTokenAuthentication;
import io.ehdev.conrad.database.model.permission.ProjectApiAuthentication;
import io.ehdev.conrad.database.model.permission.RepositoryApiAuthentication;
import io.ehdev.conrad.database.model.user.ApiRepoUserPermission;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.UserPermissionsTable;
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao;
import io.ehdev.conrad.db.tables.daos.UserDetailsDao;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
import io.ehdev.conrad.db.tables.pojos.RepoDetails;
import io.ehdev.conrad.db.tables.pojos.UserDetails;
import io.ehdev.conrad.db.tables.records.UserPermissionsRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultPermissionManagementApi implements PermissionManagementApiInternal {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPermissionManagementApi.class);

    private final DSLContext dslContext;
    private final UserDetailsDao userDetailsDao;
    private final RepoManagementApiInternal repoManagementApi;
    private final ProjectDetailsDao projectDetailsDao;

    @Autowired
    public DefaultPermissionManagementApi(DSLContext dslContext,
                                          UserDetailsDao userDetailsDao,
                                          RepoManagementApiInternal repoManagementApi,
                                          ProjectDetailsDao projectManagementApi) {
        this.dslContext = dslContext;
        this.userDetailsDao = userDetailsDao;
        this.repoManagementApi = repoManagementApi;
        this.projectDetailsDao = projectManagementApi;
    }

    @Override
    public boolean doesUserHavePermission(ApiTokenAuthentication apiUser, String project, String repoName, ApiUserPermission permission) {
        Optional<RepoDetails> repository = repoManagementApi.findRepository(project, repoName);

        if(repository.isPresent() && permission == ApiUserPermission.READ && repository.get().getPublic() ) {
            return true;
        }

        if(apiUser instanceof ProjectApiAuthentication || apiUser instanceof RepositoryApiAuthentication) {
            return isApiUserAllowed(apiUser, project, repoName, permission);
        }

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

    private boolean isApiUserAllowed(ApiTokenAuthentication user, String project, String repoName, ApiUserPermission permission) {
        if (user instanceof ProjectApiAuthentication) {
            ProjectApiAuthentication projectApiAuthentication = (ProjectApiAuthentication) user;
            return projectApiAuthentication.getProjectName().equals(project) && ApiUserPermission.ADMIN != permission;
        }

        if (user instanceof RepositoryApiAuthentication) {
            RepositoryApiAuthentication repoApiAuthentication = (RepositoryApiAuthentication) user;
            return repoApiAuthentication.getProjectName().equals(project)
                && repoApiAuthentication.getRepoName().equals(repoName)
                && ApiUserPermission.ADMIN != permission;
        }
        return false;
    }

    @Override
    public boolean addPermission(String username, ApiTokenAuthentication authenticatedUser, String projectName, String repoName, ApiUserPermission permission) {
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
            ProjectDetails details = projectDetailsDao.fetchOneByProjectName(projectName);
            doInsert(details.getProjectName(), details.getUuid(), null, null, userDetails, permission);
        }
        return true;
    }

    @Override
    public List<ApiRepoUserPermission> getPermissionsForProject(ApiParameterContainer repoModel) {
        UserPermissionsTable up = Tables.USER_PERMISSIONS;

        //@formatter:off
        Result<Record> fetch = dslContext
            .select()
            .from(up)
            .where(up.PROJECT_NAME.eq(repoModel.getProjectName()))
                .and(up.REPO_NAME.eq(repoModel.getRepoName()).or(up.REPO_NAME.isNull()))
            .fetch();
        //@formatter:on

        return fetch.into(UserPermissionsRecord.class).stream().map(this::convert).collect(Collectors.toList());
    }

    private ApiRepoUserPermission convert(UserPermissionsRecord record) {
        UserDetails userDetails = userDetailsDao.fetchOneByUuid(record.getUserUuid());
        return new ApiRepoUserPermission(userDetails.getUserName(), ApiUserPermission.findById(record.getPermissions()).name());
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
