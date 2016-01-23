package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.database.internal.VersionBumperModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VersionBumperRepository extends JpaRepository<VersionBumperModel, UUID> {

    VersionBumperModel findByBumperName(String name);

}
