package io.ehdev.conrad.database.internal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepoModelRepository extends JpaRepository<RepoModel, UUID> {
}
