package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.database.model.VcsRepoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VcsRepoRepository extends JpaRepository<VcsRepoModel, Long> {

    VcsRepoModel findByUuid(UUID uuid);
}
