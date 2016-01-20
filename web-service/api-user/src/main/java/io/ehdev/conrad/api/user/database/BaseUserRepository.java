package io.ehdev.conrad.api.user.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseUserRepository extends JpaRepository<BaseUserModel, UUID> {
}
