package io.ehdev.conrad.database.impl.repo;

import io.ehdev.conrad.database.impl.commit.CommitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RepoModelRepository extends JpaRepository<RepoModel, UUID> {

    @Query("select r from RepoModel r where r.repoName = :repoName and r.projectModel.projectName = :projectName")
    RepoModel findByProjectNameAndRepoName(@Param("projectName") String projectName, @Param("repoName") String repoName);
}
