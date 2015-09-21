package io.ehdev.version.repository;

import io.ehdev.version.commit.model.RepositoryCommitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommitModelRepository extends JpaRepository<RepositoryCommitModel, Long> {

    @Query("select rcm from RepositoryCommitModel rcm join rcm.scmMetaDataModel scm where rcm.commitId = :commitId and scm.uuid = :repoId ")
    RepositoryCommitModel findByCommitIdAndRepoId(@Param("commitId") String commitId, @Param("repoId") String repoId);

}
