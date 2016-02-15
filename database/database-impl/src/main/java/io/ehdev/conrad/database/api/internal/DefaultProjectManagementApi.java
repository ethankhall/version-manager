package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.exception.ProjectAlreadyExistsException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.project.ApiProjectModel;
import io.ehdev.conrad.database.model.project.ApiVersionBumperModel;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
import io.ehdev.conrad.db.tables.pojos.VersionBumpers;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultProjectManagementApi implements ProjectManagementApiInternal {

    private final DSLContext dslContext;
    private final ProjectDetailsDao projectDetailsDao;

    @Autowired
    public DefaultProjectManagementApi(ProjectDetailsDao projectDetailsDao,
                                       DSLContext dslContext) {
        this.dslContext = dslContext;
        this.projectDetailsDao = projectDetailsDao;
    }

    @Override
    public ApiProjectModel createProject(String projectName) throws ProjectAlreadyExistsException {
        Optional<ProjectDetails> project = findProject(projectName);
        if(project.isPresent()) {
            throw new ProjectAlreadyExistsException(projectName);
        }

        ProjectDetails projectDetails = dslContext.insertInto(Tables.PROJECT_DETAILS, Tables.PROJECT_DETAILS.PROJECT_NAME)
            .values(projectName)
            .returning(Tables.PROJECT_DETAILS.fields())
            .fetchOne()
            .into(ProjectDetails.class);

        return ModelConversionUtility.toApiModel(projectDetails, new ArrayList<>());
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
    public Optional<ProjectDetails> findProject(String projectName) {
        return Optional.ofNullable(projectDetailsDao.fetchOneByProjectName(projectName));
    }
}
