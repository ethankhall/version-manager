package io.ehdev.version.model.repository;

import io.ehdev.version.model.commit.model.RepositoryCommitModel;
import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CommitModelRepository extends JpaRepository<RepositoryCommitModel, Long> {

    @Query("select rcm from RepositoryCommitModel rcm join rcm.scmMetaDataModel scm where rcm.commitId = :commitId and scm.uuid = :repoId ")
    RepositoryCommitModel findByCommitIdAndRepoId(@Param("commitId") String commitId, @Param("repoId") String repoId);

    @Query("select rcm from RepositoryCommitModel rcm where rcm.commitId in(:commitIds) and rcm.scmMetaDataModel = :metaDataModel")
    List<RepositoryCommitModel> findCommits(@Param("metaDataModel") ScmMetaDataModel metaDataModel,
                                            @Param("commitIds") Collection<String> commitIds);

    @Query("select count(rcm) from RepositoryCommitModel rcm join rcm.scmMetaDataModel scm where scm.uuid = :repoId ")
    long countByRepoId(@Param("repoId") String repoId);
}
