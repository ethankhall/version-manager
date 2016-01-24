package io.ehdev.conrad.database.impl.commit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommitMetadataModelRepository extends JpaRepository<CommitMetadataModel, UUID> {
}
