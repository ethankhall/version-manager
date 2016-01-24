package io.ehdev.conrad.database.repository;

import io.ehdev.conrad.database.impl.VcsRepoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VcsRepoRepository extends JpaRepository<VcsRepoModel, UUID> {

}
