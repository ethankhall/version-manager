package io.ehdev.conrad.model.repository;

import io.ehdev.conrad.model.commit.model.ScmMetaDataModel;
import io.ehdev.conrad.model.commit.model.RepositoryCommitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CommitModelRepository extends JpaRepository<RepositoryCommitModel, Long> {

    @Query("select rcm from RepositoryCommitModel rcm join rcm.scmMetaDataModel scm where rcm.commitId = :commitId and scm.uuid = :repoId ")
    RepositoryCommitModel findByCommitIdAndRepoId(@Param("commitId") String commitId, @Param("repoId") UUID repoId);

    @Query("select rcm from RepositoryCommitModel rcm where rcm.commitId in (:commitIds) and rcm.scmMetaDataModel = :metaDataModel")
    List<RepositoryCommitModel> findCommits(@Param("metaDataModel") ScmMetaDataModel metaDataModel,
                                            @Param("commitIds") Collection<String> commitIds);

    @Query("select count(rcm) from RepositoryCommitModel rcm join rcm.scmMetaDataModel scm where scm.uuid = :repoId ")
    long countByRepoId(@Param("repoId") UUID repoId);
}
