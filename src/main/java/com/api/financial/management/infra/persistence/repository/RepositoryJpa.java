package com.api.financial.management.infra.persistence.repository;

import com.api.financial.management.infra.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryJpa extends JpaRepository<UsuarioJpaEntity, UUID> {
    boolean existsByEmail(String email);
    Optional<UsuarioJpaEntity> findByLogin(String login);
}
