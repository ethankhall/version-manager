package io.ehdev.version.model.repository;

import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScmMetaDataRepository extends JpaRepository<ScmMetaDataModel, Long> {

    ScmMetaDataModel findByUuid(UUID uuid);
}
