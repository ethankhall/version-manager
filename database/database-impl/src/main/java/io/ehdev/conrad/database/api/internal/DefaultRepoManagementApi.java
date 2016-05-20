package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.RepoManagementApi;
import io.ehdev.conrad.database.api.exception.BumperNotFoundException;
import io.ehdev.conrad.database.api.exception.ProjectNotFoundException;
import io.ehdev.conrad.database.api.exception.RepoAlreadyExistsException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.database.model.repo.RepoCreateModel;
import io.ehdev.conrad.database.model.repo.details.ResourceDetails;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.CommitDetailsTable;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.daos.CommitDetailsDao;
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao;
import io.ehdev.conrad.db.tables.daos.RepoDetailsDao;
import io.ehdev.conrad.db.tables.daos.VersionBumpersDao;
import io.ehdev.conrad.db.tables.pojos.CommitDetails;
import io.ehdev.conrad.db.tables.pojos.RepoDetails;
import io.ehdev.conrad.db.tables.pojos.VersionBumpers;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.ehdev.conrad.database.impl.ModelConversionUtility.toApiModel;

@Service
public class DefaultRepoManagementApi implements RepoManagementApi {

    private final DSLContext dslContext;
    private final RepoDetailsDao repoDetailsDao;
    private final VersionBumpersDao versionBumpersDao;
    private final CommitDetailsDao commitDetailsDao;

    @Autowired
    public DefaultRepoManagementApi(DSLContext dslContext,
                                    RepoDetailsDao repoDetailsDao,
                                    VersionBumpersDao versionBumpersDao,
                                    CommitDetailsDao commitDetailsDao) {
        this.dslContext = dslContext;
        this.repoDetailsDao = repoDetailsDao;
        this.versionBumpersDao = versionBumpersDao;
        this.commitDetailsDao = commitDetailsDao;
    }

    @Override
    public ApiRepoDetailsModel createRepo(RepoCreateModel repoCreateModel) {
        ResourceDetails resourceDetails = repoCreateModel.getResourceDetails();
        if (resourceDetails.getRepoId() != null && resourceDetails.getRepoId().getId() != null) {
            throw new RepoAlreadyExistsException(resourceDetails);
        }

        VersionBumpers versionBumpers = versionBumpersDao.fetchOneByBumperName(repoCreateModel.getBumperName());
        if (versionBumpers == null) {
            throw new BumperNotFoundException(repoCreateModel.getBumperName());
        }

        if (resourceDetails.getProjectId().getId() == null) {
            throw new ProjectNotFoundException(resourceDetails.getProjectId().getName());
        }

        RepoDetails repoDetails = new RepoDetails(null,
            resourceDetails.getRepoId().getName(),
            resourceDetails.getProjectId().getId(),
            versionBumpers.getUuid(),
            repoCreateModel.getUrl(),
            repoCreateModel.getDescription(),
            repoCreateModel.isPublic());

        repoDetailsDao.insert(repoDetails);

        return new ApiRepoDetailsModel(toApiModel(repoDetails), toApiModel(versionBumpers));
    }

    @Override
    public Optional<ApiCommitModel> findCommit(ResourceDetails resourceDetails, String apiCommit) {
        return findCommitInternal(resourceDetails, apiCommit).map(ModelConversionUtility::toApiModel);
    }

    @Override
    public Optional<ApiCommitModel> findLatestCommit(ResourceDetails resourceDetails, List<ApiCommitModel> history) {
        if (!history.isEmpty() && "latest".equalsIgnoreCase(history.get(0).getCommitId())) {
            return findCommit(resourceDetails, "latest");
        }

        List<String> commitIds = history.stream().map(ApiCommitModel::getCommitId).collect(Collectors.toList());

        CommitDetailsTable cd = Tables.COMMIT_DETAILS.as("cd");
        RepoDetailsTable rd = Tables.REPO_DETAILS.as("rd");
        //@formatter:off
        Record record = dslContext
            .select()
            .from(cd)
            .where(cd.REPO_DETAILS_UUID.eq(resourceDetails.getRepoId().getId()))
                .and(cd.COMMIT_ID.in(commitIds))
            .orderBy(cd.CREATED_AT.desc())
            .limit(1)
            .fetchOne();

        //@formatter:on
        return Optional.ofNullable(record).map(it -> it.into(CommitDetails.class)).map(ModelConversionUtility::toApiModel);
    }

    @Override
    public void createCommit(ResourceDetails resourceDetails, @NotNull ApiCommitModel nextVersion, ApiCommitModel history) {

        Optional<CommitDetails> parentCommit = findCommitInternal(resourceDetails, getHistoryOrNull(history));
        UUID parentUuid = parentCommit.isPresent() ? parentCommit.get().getUuid() : null;

        CommitDetails commitDetails = new CommitDetails(null,
            resourceDetails.getRepoId().getId(),
            parentUuid,
            nextVersion.getCommitId(),
            nextVersion.getVersion(),
            Instant.now());

        commitDetailsDao.insert(commitDetails);
    }

    private String getHistoryOrNull(ApiCommitModel history) {
        if(history == null) {
            return null;
        } else {
            return history.getCommitId();
        }
    }

    private Optional<CommitDetails> findCommitInternal(ResourceDetails resourceDetails, String commitId) {
        CommitDetailsTable cd = Tables.COMMIT_DETAILS.as("cd");

        SelectConditionStep<Record> query = dslContext
            .select()
            .from(cd)
            .where(cd.REPO_DETAILS_UUID.eq(resourceDetails.getRepoId().getId()));

        Record record;

        if ("latest".equalsIgnoreCase(commitId)) {
            record = query.orderBy(cd.CREATED_AT.desc())
                .limit(1)
                .fetchOne();
        } else {
            record = query
                .and(cd.COMMIT_ID.eq(commitId).or(cd.VERSION.eq(commitId)))
                .fetchOne();
        }
        return Optional.ofNullable(record).map(it -> it.into(CommitDetails.class));
    }

    @Override
    public Map<String, ApiRepoDetailsModel> getAllRepos() {
        Map<UUID, VersionBumpers> bumpers = versionBumpersDao
            .findAll()
            .stream()
            .collect(Collectors.toMap(VersionBumpers::getUuid, Function.identity()));

        return repoDetailsDao
            .findAll()
            .stream()
            .map(it -> new ApiRepoDetailsModel(toApiModel(it), toApiModel(bumpers.get(it.getVersionBumperUuid()))))
            .collect(Collectors.toMap(ApiRepoDetailsModel::getMergedName, Function.identity()));
    }

    @Override
    public List<ApiCommitModel> findAllCommits(ResourceDetails resourceDetails) {
        CommitDetailsTable cd = Tables.COMMIT_DETAILS.as("cd");

        List<CommitDetails> commits = dslContext
            .select()
            .from(cd)
            .where(cd.REPO_DETAILS_UUID.eq(resourceDetails.getRepoId().getId()))
            .fetch()
            .into(CommitDetails.class);

        return commits
            .stream()
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ApiRepoDetailsModel> getDetails(ResourceDetails resourceDetails) {
        if(resourceDetails.getRepoId().exists()) {
            RepoDetails repoDetails = repoDetailsDao.fetchOneByUuid(resourceDetails.getRepoId().getId());
            VersionBumpers versionBumpers = versionBumpersDao.fetchOneByUuid(repoDetails.getVersionBumperUuid());

            return Optional.of(new ApiRepoDetailsModel(toApiModel(repoDetails), toApiModel(versionBumpers)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean doesRepoExist(ResourceDetails resourceDetails) {
        return resourceDetails.getRepoId().exists();
    }

    @Override
    public void delete(ResourceDetails resourceDetails) {
        repoDetailsDao.deleteById(resourceDetails.getRepoId().getId());
    }
}
