package io.ehdev.conrad.database.impl.bumper;

import io.ehdev.conrad.model.internal.ApiVersionBumper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VersionBumperModelRepository extends JpaRepository<VersionBumperModel, UUID> {
    VersionBumperModel findByBumperName(String name);

    @Query("select b from VersionBumperModel b where b.projectModel.projectName = :projectName or b.projectModel IS NULL")
    List<ApiVersionBumper> findAvailableBumpers(@Param("projectName") String projectName);
}
