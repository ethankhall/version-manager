package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.database.impl.CommitModel;
import io.ehdev.conrad.database.impl.VcsRepoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CommitModelRepository extends JpaRepository<CommitModel, UUID> {

    @Query("select rcm from CommitModel rcm join rcm.vcsRepoModel repo where rcm.commitId = :commitId and repo.id = :repoId ")
    CommitModel findByCommitIdAndRepoId(@Param("commitId") String commitId, @Param("repoId") UUID repoId);

    @Query("select rcm from CommitModel rcm where rcm.commitId in (:commitIds) and rcm.vcsRepoModel = :vcsRepoModel")
    List<CommitModel> findCommits(@Param("vcsRepoModel") VcsRepoModel vcsRepoModel,
                                  @Param("commitIds") Collection<String> commitIds);

    @Query("select count(rcm) from CommitModel rcm join rcm.vcsRepoModel repo where repo.id = :repoId ")
    long countByRepoId(@Param("repoId") UUID repoId);

    @Query("select rcm from CommitModel rcm where rcm.vcsRepoModel = :vcsRepoModel")
    List<CommitModel> findCommits(@Param("vcsRepoModel") VcsRepoModel vcsRepoModel);

    @Query("select rcm from CommitModel rcm where rcm.vcsRepoModel = :vcsRepoModel and rcm.version = :version")
    CommitModel findByRepoIdAndVersion(@Param("vcsRepoModel") VcsRepoModel vcsRepoModel, @Param("version") CommitVersion version);
}
