package io.ehdev.version.repository;

import io.ehdev.version.commit.model.RepositoryCommitModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitModelRepository extends JpaRepository<RepositoryCommitModel, Long> {

    RepositoryCommitModel findByCommitId(String commitId);

}
