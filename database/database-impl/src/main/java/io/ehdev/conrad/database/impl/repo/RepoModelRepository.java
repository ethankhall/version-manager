package io.ehdev.conrad.database.impl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RepoModelRepository extends JpaRepository<RepoModel, UUID> {

    @Query("select r from RepoModel r where r.repoName = :repoName and r.projectModel.projectName = :projectName")
    RepoModel findByProjectNameAndRepoName(@Param("projectName") String projectName, @Param("repoName") String repoName);

    @Query("select CASE WHEN COUNT(r) > 0 THEN true ELSE false END from RepoModel r where r.repoName = :repoName and r.projectModel.projectName = :projectName")
    boolean doesRepoExist(String projectName, String repoName);

}
