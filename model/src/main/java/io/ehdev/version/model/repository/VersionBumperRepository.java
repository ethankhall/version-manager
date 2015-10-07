package io.ehdev.version.model.repository;

import io.ehdev.version.model.commit.model.VersionBumperModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionBumperRepository extends JpaRepository<VersionBumperModel, Long> {

    VersionBumperModel findByBumperName(String name);

}
