package io.ehdev.conrad.database.api.internal.v2;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.api.exception.ProjectCreationMustBeDoneByUserException;
import io.ehdev.conrad.database.api.v2.PermissionManagementApiV2;
import io.ehdev.conrad.database.api.v2.ProjectManagementApiV2;
import io.ehdev.conrad.database.api.v2.details.AuthUserDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceDetails;
import io.ehdev.conrad.database.api.v2.details.ResourceId;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.project.ApiProjectRepositoryDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.ProjectDetailsTable;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProjectManagementApiV2Impl implements ProjectManagementApiV2 {

    private final DSLContext dslContext;
    private final PermissionManagementApiV2 permissionManagementApiV2;

    @Autowired
    public ProjectManagementApiV2Impl(DSLContext dslContext, PermissionManagementApiV2 permissionManagementApiV2) {
        this.dslContext = dslContext;
        this.permissionManagementApiV2 = permissionManagementApiV2;
    }


    @Override
    public void createProject(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) throws ProjectAlreadyExistsException {
        if(resourceDetails.getProjectId().exists()) {
            throw new ProjectAlreadyExistsException(resourceDetails.getProjectId().getName());
        }

        if(!authUserDetails.isAuthenticationUser()) {
            throw new ProjectCreationMustBeDoneByUserException();
        }

        dslContext.insertInto(Tables.PROJECT_DETAILS, Tables.PROJECT_DETAILS.PROJECT_NAME)
            .values(resourceDetails.getProjectId().getName())
            .returning(Tables.PROJECT_DETAILS.fields())
            .fetchOne()
            .into(ProjectDetails.class);

        permissionManagementApiV2.addPermission(authUserDetails, resourceDetails, ApiUserPermission.ADMIN);
    }

    @Override
    public Optional<ApiProjectDetails> getProjectDetails(ResourceDetails resourceDetails) {
        RepoDetailsTable repoDetailsTable = Tables.REPO_DETAILS;

        ResourceId projectId = resourceDetails.getProjectId();
        if(!projectId.exists()) {
            return Optional.empty();
        }

        List<RepoDetailsRecord> repoDetails = dslContext
            .select()
            .from(repoDetailsTable)
            .where(repoDetailsTable.PROJECT_UUID.eq(projectId.getId()))
            .fetch()
            .into(repoDetailsTable);

        ApiProjectDetails apiProjectDetails = new ApiProjectDetails(projectId.getName());

        repoDetails.forEach(it -> apiProjectDetails.addDetails(new ApiProjectRepositoryDetails(it.getRepoName())));
        return Optional.of(apiProjectDetails);
    }
}
