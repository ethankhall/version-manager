package io.ehdev.conrad.database.impl.commit;

import io.ehdev.conrad.database.impl.repo.RepoModel;
import io.ehdev.conrad.model.internal.ApiCommit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommitModelRepository extends JpaRepository<CommitModel, UUID> {

    @Query("select c from CommitModel c where c.commitId in (:commitIds) and c.repoModel = :repoModel")
    List<CommitModel> findMatchingCommits(@Param("repoModel") RepoModel repoModel, @Param("commitIds") List<String> commits);

    @Query("select c from CommitModel c where (c.commitId = :commit or c.version = :commit) and c.repoModel.repoName = :repoName and c.repoModel.projectModel.projectName = :projectName")
    CommitModel findByCommitId(@Param("projectName") String projectName, @Param("repoName") String repoName, @Param("commit") String commit);

    @Query("select c from CommitModel c where c.repoModel.repoName = :repoName and c.repoModel.projectModel.projectName = :projectName")
    List<CommitModel> findAllByProjectNameAndRepoName(@Param("projectName") String projectName, @Param("repoName") String repoName);
}
