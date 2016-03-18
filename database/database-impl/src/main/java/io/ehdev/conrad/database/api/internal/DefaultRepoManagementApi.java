package io.ehdev.conrad.database.api.internal;

import io.ehdev.conrad.database.api.exception.BumperNotFoundException;
import io.ehdev.conrad.database.api.exception.ProjectNotFoundException;
import io.ehdev.conrad.database.api.exception.RepoAlreadyExistsException;
import io.ehdev.conrad.database.api.exception.RepoDoesNotExistsException;
import io.ehdev.conrad.database.impl.ModelConversionUtility;
import io.ehdev.conrad.database.model.project.ApiFullRepoModel;
import io.ehdev.conrad.database.model.project.ApiRepoDetailsModel;
import io.ehdev.conrad.database.model.project.ApiRepoModel;
import io.ehdev.conrad.database.model.project.commit.ApiCommitModel;
import io.ehdev.conrad.db.Tables;
import io.ehdev.conrad.db.tables.CommitDetailsTable;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.daos.CommitDetailsDao;
import io.ehdev.conrad.db.tables.daos.ProjectDetailsDao;
import io.ehdev.conrad.db.tables.daos.RepoDetailsDao;
import io.ehdev.conrad.db.tables.daos.VersionBumpersDao;
import io.ehdev.conrad.db.tables.pojos.CommitDetails;
import io.ehdev.conrad.db.tables.pojos.ProjectDetails;
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
public class DefaultRepoManagementApi implements RepoManagementApiInternal {

    private final DSLContext dslContext;
    private final RepoDetailsDao repoDetailsDao;
    private final ProjectDetailsDao projectDetailsDao;
    private final VersionBumpersDao versionBumpersDao;
    private final CommitDetailsDao commitDetailsDao;

    @Autowired
    public DefaultRepoManagementApi(DSLContext dslContext,
                                    RepoDetailsDao repoDetailsDao,
                                    ProjectDetailsDao projectDetailsDao,
                                    VersionBumpersDao versionBumpersDao,
                                    CommitDetailsDao commitDetailsDao) {
        this.dslContext = dslContext;
        this.repoDetailsDao = repoDetailsDao;
        this.projectDetailsDao = projectDetailsDao;
        this.versionBumpersDao = versionBumpersDao;
        this.commitDetailsDao = commitDetailsDao;
    }

    @Override
    public ApiRepoDetailsModel createRepo(ApiFullRepoModel qualifiedRepo, String bumperName, boolean isPublic) {
        Optional<RepoDetails> exists = findRepository(qualifiedRepo.getProjectName(), qualifiedRepo.getRepoName());
        if (exists.isPresent()) {
            throw new RepoAlreadyExistsException(qualifiedRepo);
        }

        VersionBumpers versionBumpers = versionBumpersDao.fetchOneByBumperName(bumperName);
        if (versionBumpers == null) {
            throw new BumperNotFoundException(bumperName);
        }

        ProjectDetails projectDetails = projectDetailsDao.fetchOneByProjectName(qualifiedRepo.getProjectName());
        if (projectDetails == null) {
            throw new ProjectNotFoundException(qualifiedRepo.getProjectName());
        }

        RepoDetails repoDetails = new RepoDetails(null,
            qualifiedRepo.getProjectName(),
            qualifiedRepo.getRepoName(),
            projectDetails.getUuid(),
            versionBumpers.getUuid(),
            qualifiedRepo.getUrl(),
            "",
            isPublic);

        repoDetailsDao.insert(repoDetails);

        return new ApiRepoDetailsModel(toApiModel(repoDetails), toApiModel(versionBumpers));
    }

    public Optional<RepoDetails> findRepository(String projectName, String repoName) {
        //@formatter:off
        return Optional.ofNullable(
            dslContext
                    .select()
                    .from(Tables.REPO_DETAILS)
                    .where(Tables.REPO_DETAILS.PROJECT_NAME.equal(projectName))
                        .and(Tables.REPO_DETAILS.REPO_NAME.equal(repoName))
                    .fetchOne()
        ).map(it -> it.into(RepoDetails.class));
        //@formatter:on
    }

    @Override
    public Optional<ApiCommitModel> findCommit(ApiRepoModel repo, String apiCommit) {
        return findCommitInternal(repo, apiCommit).map(ModelConversionUtility::toApiModel);
    }

    @Override
    public Optional<ApiCommitModel> findLatestCommit(ApiRepoModel repo, List<ApiCommitModel> history) {
        if (!history.isEmpty() && "latest".equalsIgnoreCase(history.get(0).getCommitId())) {
            return findCommit(repo, "latest");
        }

        List<String> commitIds = history.stream().map(ApiCommitModel::getCommitId).collect(Collectors.toList());

        CommitDetailsTable cd = Tables.COMMIT_DETAILS.as("cd");
        RepoDetailsTable rd = Tables.REPO_DETAILS.as("rd");
        //@formatter:off
        Record record = createQueryForCommitsForRepo(repo, cd, rd)
                .and(cd.COMMIT_ID.in(commitIds))
            .orderBy(cd.CREATED_AT.desc())
            .limit(1)
            .fetchOne();

        //@formatter:on
        return Optional.ofNullable(record).map(it -> it.into(CommitDetails.class)).map(ModelConversionUtility::toApiModel);
    }

    @Override
    public void createCommit(ApiRepoModel apiRepo, @NotNull ApiCommitModel nextVersion, ApiCommitModel history) {
        Optional<RepoDetails> repository = findRepository(apiRepo.getProjectName(), apiRepo.getRepoName());
        if(!repository.isPresent()) {
            throw new RepoDoesNotExistsException(apiRepo);
        }
        RepoDetails repoDetails = repository.get();

        Optional<CommitDetails> parentCommit = findCommitInternal(apiRepo, getHistoryOrNull(history));
        UUID parentUuid = parentCommit.isPresent() ? parentCommit.get().getUuid() : null;

        CommitDetails commitDetails = new CommitDetails(null,
            repoDetails.getUuid(),
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

    private Optional<CommitDetails> findCommitInternal(ApiRepoModel repo, String commitId) {
        CommitDetailsTable cd = Tables.COMMIT_DETAILS.as("cd");
        RepoDetailsTable rd = Tables.REPO_DETAILS.as("rd");
        SelectConditionStep<Record> query = createQueryForCommitsForRepo(repo, cd, rd);

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

    private SelectConditionStep<Record> createQueryForCommitsForRepo(ApiRepoModel repo,
                                                                     CommitDetailsTable cd,
                                                                     RepoDetailsTable rd) {
        //@formatter:on
        return dslContext
            .select(cd.fields())
            .from(cd)
            .join(rd)
                .on(cd.REPO_DETAILS_UUID.equal(rd.UUID))
            .where(rd.PROJECT_NAME.equal(repo.getProjectName()))
                .and(rd.REPO_NAME.equal(repo.getRepoName()));
        //@formatter:off
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
    public List<ApiCommitModel> findAllCommits(ApiRepoModel repo) {
        CommitDetailsTable cd = Tables.COMMIT_DETAILS.as("cd");
        RepoDetailsTable rd = Tables.REPO_DETAILS.as("rd");

        SelectConditionStep<Record> query = createQueryForCommitsForRepo(repo, cd, rd);
        List<CommitDetails> commits = query.fetch().into(CommitDetails.class);

        return commits
            .stream()
            .map(ModelConversionUtility::toApiModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ApiRepoDetailsModel> getDetails(ApiRepoModel repoModel) {
        Optional<RepoDetails> details = findRepository(repoModel.getProjectName(), repoModel.getRepoName());
        if(details.isPresent()) {
            RepoDetails repoDetails = details.get();
            VersionBumpers versionBumpers = versionBumpersDao.fetchOneByUuid(repoDetails.getVersionBumperUuid());

            return Optional.of(new ApiRepoDetailsModel(toApiModel(repoDetails), toApiModel(versionBumpers)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean doesRepoExist(ApiRepoModel repo) {
        return findRepository(repo.getProjectName(), repo.getRepoName()).isPresent();
    }

}
