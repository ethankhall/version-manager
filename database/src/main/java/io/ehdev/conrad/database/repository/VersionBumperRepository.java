package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.database.model.VersionBumperModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionBumperRepository extends JpaRepository<VersionBumperModel, Long> {

    VersionBumperModel findByBumperName(String name);

}
