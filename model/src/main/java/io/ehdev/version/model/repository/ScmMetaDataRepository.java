package io.ehdev.version.model.repository;

import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScmMetaDataRepository extends JpaRepository<ScmMetaDataModel, Long> {

    ScmMetaDataModel findByRepoName(String repository);

    ScmMetaDataModel findByUuid(String uuid);
}
