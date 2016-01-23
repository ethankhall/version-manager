package io.ehdev.conrad.database.internal.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectModelRepository extends JpaRepository<ProjectModel, UUID> {
}
