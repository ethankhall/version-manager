package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.database.model.CommitMetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommitMetadataModelRepository extends JpaRepository<CommitMetadataModel, UUID> {

}
