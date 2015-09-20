package io.ehdev.version.repository;

import io.ehdev.version.commit.model.ScmMetaDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScmMetaDataRepository extends JpaRepository<ScmMetaDataModel, Long> {

    public ScmMetaDataModel findByRepoName(String repository);
}
