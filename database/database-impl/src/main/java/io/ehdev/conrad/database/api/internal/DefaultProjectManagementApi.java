package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.project.ApiProjectDetails;
import io.ehdev.conrad.database.model.project.ApiProjectRepositoryDetails;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
import io.ehdev.conrad.db.tables.pojos.VersionBumpers;
import io.ehdev.conrad.db.tables.records.ProjectDetailsRecord;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultProjectManagementApi implements ProjectManagementApiInternal {

    private final DSLContext dslContext;
    private final ProjectDetailsDao projectDetailsDao;
    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public DefaultProjectManagementApi(ProjectDetailsDao projectDetailsDao,
                                       DSLContext dslContext,
                                       PermissionManagementApi permissionManagementApi) {
        this.dslContext = dslContext;
        this.projectDetailsDao = projectDetailsDao;
        this.permissionManagementApi = permissionManagementApi;
    }

    @Override
    public void createProject(ApiParameterContainer apiParameterContainer) throws ProjectAlreadyExistsException {
        String projectName = apiParameterContainer.getProjectName();
        Optional<ProjectDetails> project = findProject(projectName);
        if(project.isPresent()) {
            throw new ProjectAlreadyExistsException(projectName);
        }

        dslContext.insertInto(Tables.PROJECT_DETAILS, Tables.PROJECT_DETAILS.PROJECT_NAME)
            .values(projectName)
            .returning(Tables.PROJECT_DETAILS.fields())
            .fetchOne()
            .into(ProjectDetails.class);

        String userName = apiParameterContainer.getUser().getUserName();
        permissionManagementApi.forceAddPermission(userName, projectName, null, ApiUserPermission.ADMIN);
    }

    @Override
    public List<ApiVersionBumperModel> findAllVersionBumpers(String projectName) {
        Result<Record> fetch = dslContext
            .select()
            .from(Tables.VERSION_BUMPERS)
            .fetch();

        List<VersionBumpers> bumpers = fetch.into(VersionBumpers.class);

        return bumpers
            .stream()
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }

    @Override
    public ApiProjectDetails getProjectDetails(ApiParameterContainer apiParameterContainer) {
        ProjectDetailsRecord projectDetails = dslContext
            .select()
            .from(Tables.PROJECT_DETAILS)
            .where(Tables.PROJECT_DETAILS.PROJECT_NAME.eq(apiParameterContainer.getProjectName()))
            .fetchOne()
            .into(Tables.PROJECT_DETAILS);

        List<RepoDetailsRecord> repoDetails = dslContext
            .select()
            .from(Tables.REPO_DETAILS)
            .where(Tables.REPO_DETAILS.PROJECT_UUID.eq(projectDetails.getUuid()))
            .fetch()
            .into(Tables.REPO_DETAILS);

        ApiProjectDetails apiProjectDetails = new ApiProjectDetails(projectDetails.getProjectName());

        repoDetails.forEach(it -> apiProjectDetails.addDetails(new ApiProjectRepositoryDetails(it.getRepoName())));
        return apiProjectDetails;
    }

    @Override
    public Optional<ProjectDetails> findProject(String projectName) {
        return Optional.ofNullable(projectDetailsDao.fetchOneByProjectName(projectName));
    }
}
