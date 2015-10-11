package io.ehdev.conrad.model.repository;

import io.ehdev.conrad.model.commit.model.VersionBumperModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionBumperRepository extends JpaRepository<VersionBumperModel, Long> {

    VersionBumperModel findByBumperName(String name);

}
