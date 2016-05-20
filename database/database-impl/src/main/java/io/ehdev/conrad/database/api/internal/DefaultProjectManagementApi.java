package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.api.exception.ProjectCreationMustBeDoneByUserException;
import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.ProjectManagementApi;
import io.ehdev.conrad.database.model.repo.details.AuthUserDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.database.model.repo.details.ResourceId;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.project.ApiProjectRepositoryDetails;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProjectManagementApi implements ProjectManagementApi {

    private final DSLContext dslContext;
    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public DefaultProjectManagementApi(DSLContext dslContext, PermissionManagementApi permissionManagementApi) {
        this.dslContext = dslContext;
        this.permissionManagementApi = permissionManagementApi;
    }


    @Override
    public ResourceId createProject(AuthUserDetails authUserDetails, ResourceDetails resourceDetails) throws ProjectAlreadyExistsException {
        if(resourceDetails.getProjectId().exists()) {
            throw new ProjectAlreadyExistsException(resourceDetails.getProjectId().getName());
        }

        if(!authUserDetails.isAuthenticationUser()) {
            throw new ProjectCreationMustBeDoneByUserException();
        }

        ProjectDetails into = dslContext.insertInto(Tables.PROJECT_DETAILS, Tables.PROJECT_DETAILS.PROJECT_NAME)
            .values(resourceDetails.getProjectId().getName())
            .returning(Tables.PROJECT_DETAILS.fields())
            .fetchOne()
            .into(ProjectDetails.class);

        permissionManagementApi.addPermission(authUserDetails, resourceDetails, ApiUserPermission.ADMIN);

        return new ResourceId(into.getProjectName(), into.getUuid());
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
