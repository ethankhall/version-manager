package io.ehdev.version.repository;

import io.ehdev.version.commit.model.VersionBumperModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionBumperRepository extends JpaRepository<VersionBumperModel, Long> {
}
