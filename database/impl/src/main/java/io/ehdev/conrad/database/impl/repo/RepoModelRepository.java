package io.ehdev.conrad.database.impl.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepoModelRepository extends JpaRepository<RepoModel, UUID> {
}
