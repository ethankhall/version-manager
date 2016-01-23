package io.ehdev.conrad.database.internal.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseUserRepository extends JpaRepository<BaseUserModel, UUID> {
}
